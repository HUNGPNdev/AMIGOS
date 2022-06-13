package com.amigos.product;

import com.amigos.common.ResponseApi;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

public interface ProductService {
    ResponseApi addProduct(MultipartFile image_1, MultipartFile image_2, MultipartFile image_3, String product, HttpServletRequest httpServletRequest) throws IOException;
    ResponseApi getListProduct(boolean status);
    ResponseApi getProductById(UUID id);
    ResponseApi delete(UUID id);
    ResponseApi getAllProductByCateId(UUID cateId);

}
