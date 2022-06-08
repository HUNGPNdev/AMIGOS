package com.amigos.product;

import com.amigos.common.ResponseApi;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ResponseApi addCategory(MultipartFile image_1, MultipartFile image_2, MultipartFile image_3, String product) throws IOException;
}
