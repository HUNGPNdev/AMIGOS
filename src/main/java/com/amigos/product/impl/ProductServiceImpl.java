package com.amigos.product.impl;

import com.amigos.authentication.jwt.JwtProvider;
import com.amigos.category.repository.CategoryRepository;
import com.amigos.common.ResponseApi;
import com.amigos.common.UserCommon;
import com.amigos.config.ModelMapperConfig;
import com.amigos.dto.ProductDTO;
import com.amigos.product.ProductService;
import com.amigos.product.model.ProductEntity;
import com.amigos.product.repository.ProductRepository;
import com.amigos.user.model.User;
import com.amigos.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.amigos.common.Constants.ENTITY_NOT_FOUND;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ServletContext context;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired ModelMapperConfig modelMapper;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private JwtProvider tokenProvider;

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseApi addProduct(MultipartFile image_1, MultipartFile image_2, MultipartFile image_3, String product, HttpServletRequest httpServletRequest) throws IOException {
        ProductEntity entityCreate = new ProductEntity();
        ProductDTO proD = objectMapper.readValue(product, ProductDTO.class);
        modelMapper.map(proD, entityCreate);
        entityCreate.setCreateAt(new Date());
        User createBy = UserCommon.getUserFromRequest(httpServletRequest, tokenProvider, userRepository);
        if(createBy == null) {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
        entityCreate.setUserId(createBy);
        entityCreate.setCateId(categoryRepository.findById(proD.getCateId()).get());
        setProductImages(image_1, image_2, image_3, entityCreate);
        entityCreate = productRepository.save(entityCreate);
        proD = modelMapper.map(entityCreate, ProductDTO.class);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), proD);
        return rs;
    }

    @Override
    public ResponseApi getListProduct(boolean status) {
        List<ProductEntity> products = productRepository.findAllByIsDeleted(status);
        List<ProductDTO> categoryDTOS = modelMapper.mapAll(products, ProductDTO.class);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), categoryDTOS);
        return rs;
    }

    @Override
    public ResponseApi updateProduct(MultipartFile image_1, MultipartFile image_2, MultipartFile image_3, String product, HttpServletRequest httpServletRequest) throws IOException
    {
        ProductDTO proD = objectMapper.readValue(product, ProductDTO.class);
        Optional<ProductEntity> findProduct = productRepository.findById(proD.getId());

        if (findProduct.isEmpty()) {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
        ProductEntity productUpdate = findProduct.get();
        modelMapper.map(proD, productUpdate);
        User createBy = UserCommon.getUserFromRequest(httpServletRequest, tokenProvider, userRepository);
        if(createBy == null) {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
        productUpdate.setUserId(createBy);
        productUpdate.setCateId(categoryRepository.findById(proD.getCateId()).get());
        setProductImages(image_1, image_2, image_3, productUpdate);
        productUpdate.setUpdateAt(new Date());

        productUpdate = productRepository.save(productUpdate);
        proD = modelMapper.map(productUpdate, ProductDTO.class);

        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), proD);
        return rs;
    }

    @Override
    public ResponseApi getProductById(UUID id)
    {
        Optional<ProductEntity> findProduct = productRepository.findById(id);
        if (findProduct.isEmpty()) {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
        ProductDTO proD = modelMapper.map(findProduct.get(), ProductDTO.class);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), proD);
        return rs;
    }

    @Override
    public ResponseApi delete(UUID id)
    {
        Optional<ProductEntity> findProduct = productRepository.findById(id);
        if (findProduct.isEmpty()) {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
        ProductEntity productDelete = findProduct.get();
        productDelete.setIsDeleted(Boolean.TRUE);
        productRepository.save(productDelete);
        ProductDTO proD = modelMapper.map(findProduct.get(), ProductDTO.class);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), proD);
        return rs;
    }

    private void setProductImages(MultipartFile image_1, MultipartFile image_2, MultipartFile image_3, ProductEntity entity) throws IOException
    {
        Date date = new Date();
        if(image_1 != null) {
            String fileName = date.getTime()+"1"+image_1.getOriginalFilename();
            String photoPath = context.getRealPath("images/" + fileName);
            image_1.transferTo(new File(photoPath));
            entity.setImage_1(fileName);
        }
        if(image_2 != null) {
            String fileName = date.getTime()+"2"+image_2.getOriginalFilename();
            String photoPath = context.getRealPath("images/" + fileName);
            image_2.transferTo(new File(photoPath));
            entity.setImage_2(fileName);
        }
        if(image_3 != null) {
            String fileName = date.getTime()+"3"+image_3.getOriginalFilename();
            String photoPath = context.getRealPath("images/" + fileName);
            image_3.transferTo(new File(photoPath));
            entity.setImage_3(fileName);
        }
    }
}
