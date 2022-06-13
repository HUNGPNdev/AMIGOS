package com.amigos.category.controller;

import com.amigos.category.CategoryService;
import com.amigos.category.model.CategoryEntity;
import com.amigos.common.ResponseApi;
import com.amigos.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CategoryController {

    @Autowired
    CategoryService service;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> addCategory(@RequestBody CategoryDTO category) {
        return new ResponseEntity<>(service.addCategory(category), HttpStatus.OK);
    }

    @PatchMapping("")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> updateCategory(@RequestBody CategoryDTO category) {
        return new ResponseEntity<>(service.updateCategory(category), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> getDetailCategory(@NotEmpty @PathVariable("id") UUID id) {
        return ResponseEntity.ok(service.getDetailCategory(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> delete(@NotEmpty @PathVariable("id") UUID id) {
        return ResponseEntity.ok(service.delete(id));
    }

//    @GetMapping("")
//    @PreAuthorize("permitAll()")
//    public ResponseEntity<ResponseApi> getLimit() {
//        return ResponseEntity.ok(service.getLimit());
//    }

    @GetMapping("/alls")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> getCateByCondition() {
        return ResponseEntity.ok(service.getCateByCondition());
    }

}
