package com.amigos.customerreview.controller;

import com.amigos.common.ResponseApi;
import com.amigos.customerreview.CustomerReviewService;
import com.amigos.dto.customerreview.CustomerReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@RestController
@RequestMapping("/customerreview")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CustomerReviewController {

    @Autowired
    CustomerReviewService service;

   @PostMapping("")
  // @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")

    public ResponseEntity<ResponseApi> addCustomerReview(@RequestBody CustomerReviewDto customerReviewDto, HttpServletRequest httpServletRequest) {

        return new ResponseEntity<>(service.addCustomerReview(customerReviewDto,httpServletRequest), HttpStatus.OK);
    }

    @GetMapping("/{proId}")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ResponseApi> getCustomerReviewByProduct(@NotEmpty @PathVariable("proId") UUID proId) {
        return ResponseEntity.ok(service.getCustomerReviewByProduct(proId));
    }
    @GetMapping("/checkCustomerReview/{proId}")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ResponseApi> checkCustomerReview(@NotEmpty @PathVariable("proId") UUID proId ,HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(service.CheckCustomerReview(proId,httpServletRequest), HttpStatus.OK);
    }
    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> getListCustomerReviews() {
        return  ResponseEntity.ok(service.getCustomerReview());
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> delete(@NotEmpty @PathVariable("id") UUID id) {
        return ResponseEntity.ok(service.delete(id));
    }


}
