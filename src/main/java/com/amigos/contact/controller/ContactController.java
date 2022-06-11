package com.amigos.contact.controller;

import com.amigos.common.ResponseApi;
import com.amigos.contact.ContactService;
import com.amigos.contact.model.ContactEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@RestController
@RequestMapping("/contact")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ContactController {

    @Autowired
    ContactService service;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> addContact(@RequestBody ContactEntity contact) {
        return new ResponseEntity<>(service.addContact(contact), HttpStatus.OK);
    }

    @PatchMapping("")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> updateContact(@RequestBody ContactEntity contact) {
        return new ResponseEntity<>(service.updateContact(contact), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi> getDetailContact(@NotEmpty @PathVariable("id") UUID id) {
        return ResponseEntity.ok(service.getDetailContact(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseApi> delete(@NotEmpty @PathVariable("id") UUID id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("")
    public ResponseEntity<ResponseApi> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
