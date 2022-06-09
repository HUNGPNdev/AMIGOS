package com.amigos.blog.controller;

import com.amigos.blog.BlogService;
import com.amigos.blog.model.Blog;
import com.amigos.category.CategoryService;
import com.amigos.category.model.CategoryEntity;
import com.amigos.common.ResponseApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blogs")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BlogController {
    @Autowired
    BlogService service;

    @PostMapping("") @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> addBlog(@RequestBody Blog blog) {
        return new ResponseEntity<>(service.addBlog(blog), HttpStatus.OK);
    }
}
