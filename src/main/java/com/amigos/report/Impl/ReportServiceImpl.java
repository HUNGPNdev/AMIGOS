package com.amigos.report.Impl;

import com.amigos.cartproductsize.repository.CartProductSizeRepository;
import com.amigos.category.repository.CategoryRepository;
import com.amigos.common.Constants;
import com.amigos.common.ResponseApi;
import com.amigos.customerreview.repository.CustomerReviewRepository;
import com.amigos.dto.ProductReportDTO;
import com.amigos.dto.ReportDTO;
import com.amigos.orders.repository.OrderRepository;
import com.amigos.product.model.ProductEntity;
import com.amigos.product.repository.ProductRepository;
import com.amigos.productsize.model.ProductSizeEntity;
import com.amigos.report.ReportService;
import com.amigos.user.repository.UserRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CartProductSizeRepository cartProductSizeRepository;

    @Autowired
    CustomerReviewRepository customerReviewRepository;

    @Override
    public ResponseApi createReport() throws IOException {
        FileInputStream inputStream = new FileInputStream(Constants.TEMPLATE_REPORT_PATH);
        Workbook workbook = WorkbookFactory.create(inputStream);
        ReportDTO reportDTO = reportGeneral();
        createFolderIfNotExit();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String strDate = formatter.format(new Date());

        Sheet sheet = workbook.cloneSheet(0);
        workbook.setSheetName(workbook.getSheetIndex(sheet), "Amigos-Report");

        //overview row
        Row firstRow = sheet.getRow(3);
        Cell userCell = firstRow.getCell(1);
        userCell.setCellValue(reportDTO.getUsers());
        Cell productCell = firstRow.getCell(2);
        productCell.setCellValue(reportDTO.getProducts());
        Cell categoryCell = firstRow.getCell(3);
        categoryCell.setCellValue(reportDTO.getCategories());
        Cell orderCell = firstRow.getCell(4);
        orderCell.setCellValue(reportDTO.getOrders());

        //Product row
        int startProductRow = Constants.START_ROW_SCHEMA_DETAIL_POSITION;
        Row productRowTemplate = sheet.getRow(8);
        int stt = 1;
        for (ProductReportDTO p: reportDTO.getProductDetails()) {
            Row row = sheet.getRow(startProductRow);
            if (row == null) {
                row = sheet.createRow(startProductRow);
                row.createCell(1);
                row.createCell(2);
                row.createCell(3);
                row.createCell(4);
                row.createCell(5);

            }
            Cell noRow = row.getCell(1);
            noRow.setCellValue(stt);
            noRow.setCellStyle(productRowTemplate.getCell(1).getCellStyle());

            Cell productName = row.getCell(2);
            productName.setCellValue(p.getProductName());
            productName.setCellStyle(productRowTemplate.getCell(2).getCellStyle());

            Cell categoryNameCell = row.getCell(3);
            categoryNameCell.setCellValue(p.getCategoryName());
            categoryNameCell.setCellStyle(productRowTemplate.getCell(3).getCellStyle());

            Cell inStockCell = row.getCell(4);
            inStockCell.setCellValue(p.getInStock());
            inStockCell.setCellStyle(productRowTemplate.getCell(4).getCellStyle());

            Cell soldCell = row.getCell(5);
            soldCell.setCellValue(p.getSold());
            soldCell.setCellStyle(productRowTemplate.getCell(5).getCellStyle());

            stt++;
            startProductRow++;
        }

        FileOutputStream outputStream = new FileOutputStream(String.format(Constants.LOCAL_TEMPLATE_PATH + Constants.EXCEL_OUTPUT_FILE_PATH, strDate));
        Sheet sheetRoot = workbook.getSheet(Constants.REPORT_SHEET);
        workbook.removeSheetAt(workbook.getSheetIndex(sheetRoot));
        workbook.write(outputStream);
        outputStream.close();

        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());
        return rs;
    }

    @Override
    public ResponseApi countUser() {
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), userRepository.count());
        return rs;
    }

    @Override
    public ResponseApi countComment() {
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), customerReviewRepository.count());
        return rs;
    }

    @Override
    public ResponseApi countProduct() {
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), productRepository.count());
        return rs;
    }

    @Override
    public ResponseApi countCategory() {
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), categoryRepository.count());
        return rs;
    }


    private ReportDTO reportGeneral() {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setUsers(userRepository.count());
        reportDTO.setProducts(productRepository.count());
        reportDTO.setCategories(categoryRepository.count());
        reportDTO.setOrders(orderRepository.count());
        ProductReportDTO productDetail;
        for (ProductEntity p: productRepository.findAll()) {
            productDetail = new ProductReportDTO();
            productDetail.setProductName(p.getName());
            productDetail.setCategoryName(p.getCateId().getName());
            long inStock = 0;
            for (ProductSizeEntity pz: p.getProductSizes()) {
                inStock += pz.getCount();
            }
            productDetail.setInStock(inStock);
            productDetail.setSold(cartProductSizeRepository.countReportByProductId(p.getId()));
            reportDTO.getProductDetails().add(productDetail);
        }
        return reportDTO;
    }

    private void createFolderIfNotExit() {
        Path path = Paths.get(Constants.LOCAL_TEMPLATE_PATH);
        if(!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                System.out.format("Create folder %s", path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
