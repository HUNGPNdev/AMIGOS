package com.amigos.product.impl;

import com.amigos.authentication.jwt.JwtProvider;
import com.amigos.category.repository.CategoryRepository;
import com.amigos.common.ResponseApi;
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
    public ResponseApi addCategory(MultipartFile image_1, MultipartFile image_2, MultipartFile image_3, String product, HttpServletRequest httpServletRequest) throws IOException {
        ProductEntity entityCreate = new ProductEntity();
        ProductDTO proD = objectMapper.readValue(product, ProductDTO.class);
        modelMapper.map(proD, entityCreate);
        setProductImages(image_1, image_2, image_3, entityCreate);
        entityCreate.setCateId(categoryRepository.findById(proD.getCateId()).get());
        entityCreate.setCreateAt(new Date());
        String jwt = tokenProvider.getJwt(httpServletRequest);
        String userName = tokenProvider.getUserNameFromJwtToken(jwt);
        Optional<User> createBy = userRepository.findByUserName(userName);
        if(createBy.isEmpty()) {
            return null;
        }
        entityCreate.setUserId(createBy.get());
        entityCreate = productRepository.save(entityCreate);
        proD = modelMapper.map(entityCreate, ProductDTO.class);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), proD);
        return rs;
    }

    @Override
    public ResponseApi getListProduct() {
        List<ProductEntity> products = productRepository.findAll();
        List<ProductDTO> categoryDTOS = modelMapper.mapAll(products, ProductDTO.class);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), categoryDTOS);
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
