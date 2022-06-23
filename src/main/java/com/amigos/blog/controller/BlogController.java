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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/blogs")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BlogController {
    @Autowired
    BlogService service;

    @PostMapping("") @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> addBlog(@RequestParam(value = "image", required=false) MultipartFile image,
                                               @ModelAttribute("blog") String blog, HttpServletRequest httpServletRequest) throws IOException {
        return new ResponseEntity<>(service.addBlogUpdateBlog(image,blog,httpServletRequest), HttpStatus.OK);
    }
    @GetMapping("/status/{status}")
    //@PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> getListBlog(@PathVariable("status") String status) {
        return new ResponseEntity<>(service.getListBlog(Boolean.valueOf(status)), HttpStatus.OK);
    }


    @PatchMapping("")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> updateBlog(@RequestParam(value = "image", required=false) MultipartFile image,
                                                     @ModelAttribute("blog") String blog, HttpServletRequest httpServletRequest) throws IOException {
        return new ResponseEntity<>(service.addBlogUpdateBlog(image, blog, httpServletRequest), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> getBlogById(@NotEmpty @PathVariable("id") UUID id) {
        return new ResponseEntity<>(service.getDetailBlog(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> delete(@NotEmpty @PathVariable("id") UUID id) {
        return ResponseEntity.ok(service.delete(id));
    }

}
