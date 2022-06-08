package com.amigos.product.controller;

import com.amigos.common.ResponseApi;
import com.amigos.dto.CategoryDTO;
import com.amigos.dto.ProductDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController
{
    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> addProduct(@RequestParam(value = "image_1", required=false) MultipartFile image_1,
            @RequestParam(value = "image_2", required=false) MultipartFile image_2,
            @RequestParam(value = "image_3", required=false) MultipartFile image_3,
            @ModelAttribute("product") String product) {

        return null;
    }
}
