package com.amigos.productsize.controller;

import com.amigos.common.ResponseApi;
import com.amigos.dto.ProductSizeDTO;
import com.amigos.productsize.ProductSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@RestController
@RequestMapping("/product-size")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductSizeController
{
    @Autowired
    ProductSizeService productSizeService;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> addProductSize(@RequestBody ProductSizeDTO productSizeDTO) {
        return new ResponseEntity<>(productSizeService.addProductSize(productSizeDTO), HttpStatus.OK);
    }

    @PatchMapping("")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> update(@RequestBody ProductSizeDTO productSizeDTO) {
        return new ResponseEntity<>(productSizeService.addProductSize(productSizeDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> getDetail(@NotEmpty @PathVariable("id") UUID id) {
        return ResponseEntity.ok(productSizeService.getDetail(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> delete(@NotEmpty @PathVariable("id") UUID id) {
        return ResponseEntity.ok(productSizeService.delete(id));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> getAllByStatus(@PathVariable("status") String status) {
        return new ResponseEntity<>(productSizeService.getAllByStatus(Boolean.valueOf(status)), HttpStatus.OK);
    }
}
