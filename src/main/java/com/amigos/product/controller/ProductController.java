package com.amigos.product.controller;

import com.amigos.common.ResponseApi;
import com.amigos.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController
{
    @Autowired
    ProductService productService;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> addProduct(@RequestParam(value = "image_1", required=false) MultipartFile image_1,
            @RequestParam(value = "image_2", required=false) MultipartFile image_2,
            @RequestParam(value = "image_3", required=false) MultipartFile image_3,
            @ModelAttribute("product") String product, HttpServletRequest httpServletRequest) throws IOException {
        return new ResponseEntity<>(productService.addCategory(image_1, image_2, image_3, product, httpServletRequest), HttpStatus.OK);
    }
}
