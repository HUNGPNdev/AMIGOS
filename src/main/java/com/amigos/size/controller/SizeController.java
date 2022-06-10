package com.amigos.size.controller;

import com.amigos.category.CategoryService;
import com.amigos.common.ResponseApi;
import com.amigos.dto.CategoryDTO;
import com.amigos.dto.SizeDTO;
import com.amigos.size.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@RestController
@RequestMapping("/sizes")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SizeController
{
    @Autowired
    SizeService service;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> addSize(@RequestBody SizeDTO sizeDTO) {
        return new ResponseEntity<>(service.addSize(sizeDTO), HttpStatus.OK);
    }

    @PatchMapping("")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> updateSize(@RequestBody SizeDTO sizeDTO) {
        return new ResponseEntity<>(service.updateSize(sizeDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> getDetailSize(@NotEmpty @PathVariable("id") UUID id) {
        return ResponseEntity.ok(service.getDetailSize(id));
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

}
