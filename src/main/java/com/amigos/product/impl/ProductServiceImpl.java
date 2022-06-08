package com.amigos.product.impl;

import com.amigos.common.ResponseApi;
import com.amigos.product.ProductService;
import com.amigos.product.model.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ServletContext context;

    @Override
    public ResponseApi addCategory(MultipartFile image_1, MultipartFile image_2, MultipartFile image_3, String product) throws IOException {
        ProductEntity entity = new ProductEntity();
        if(image_1 != null) {
            String photoPath = context.getRealPath("images/" + image_1.getOriginalFilename());
            String path = new ClassPathResource("/src/main/resources/image/").getPath();

            FileCopyUtils.copy(image_1.getBytes(), new File("/src/main/resources/image/" + image_1.getOriginalFilename()));
//            entity.setImage_1(image_1.getOriginalFilename());
        }
//        if(image_2 != null) {
//            String photoPath = context.getRealPath("images/" + image_2.getOriginalFilename());
//            image_2.transferTo(new File(photoPath));
//            entity.setImage_1(image_2.getOriginalFilename());
//        }
//        if(image_3 != null) {
//            String photoPath = context.getRealPath("images/" + image_3.getOriginalFilename());
//            image_3.transferTo(new File(photoPath));
//            entity.setImage_1(image_3.getOriginalFilename());
//        }



        return null;
    }
}
