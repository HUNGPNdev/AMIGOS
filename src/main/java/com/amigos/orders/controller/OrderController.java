package com.amigos.orders.controller;

import com.amigos.cartproductsize.model.EnumStatusCart;
import com.amigos.common.ResponseApi;
import com.amigos.orders.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> getAllByStatus(@PathVariable("status") String status) {
        return new ResponseEntity<>(orderService.getAlls(Boolean.valueOf(status)), HttpStatus.OK);
    }

    @GetMapping("/{id}/status/{status}")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> orderUpdateStatus(@PathVariable("id") UUID orderId, @PathVariable("status") EnumStatusCart status) {
        return new ResponseEntity<>(orderService.orderUpdateStatus(orderId, status), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> deleteById(@PathVariable("id") UUID orderId) {
        return new ResponseEntity<>(orderService.deleteById(orderId), HttpStatus.OK);
    }
}
