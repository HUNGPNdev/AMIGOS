package com.amigos.productsize.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product-size")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductSizeController
{

}
