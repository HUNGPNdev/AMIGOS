package com.amigos.role.controller;

import com.amigos.common.ResponseApi;
import com.amigos.config.ModelMapperConfig;
import com.amigos.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RoleController {
    @Autowired
    RoleService service;
    @Autowired
    ModelMapperConfig modelMapper;
    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
