package com.amigos.report.controller;

import com.amigos.common.ResponseApi;
import com.amigos.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/reports")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReportController {
    @Autowired
    ReportService reportService;

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> createReport() throws IOException {
        return new ResponseEntity<>(reportService.createReport(), HttpStatus.OK);
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> countUser() {
        return new ResponseEntity<>(reportService.countUser(), HttpStatus.OK);
    }

    @GetMapping("/comments")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> countComment() {
        return new ResponseEntity<>(reportService.countComment(), HttpStatus.OK);
    }

    @GetMapping("/products")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> countProduct() {
        return new ResponseEntity<>(reportService.countProduct(), HttpStatus.OK);
    }

    @GetMapping("/categories")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> countCategory() {
        return new ResponseEntity<>(reportService.countCategory(), HttpStatus.OK);
    }
}
