package com.amigos.user.controller;

import com.amigos.authentication.request.SignUpForm;
import com.amigos.common.ResponseApi;
import com.amigos.config.ModelMapperConfig;
import com.amigos.dto.UserDTO;
import com.amigos.dto.UserInputDto;
import com.amigos.user.model.User;
import com.amigos.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    UserService service;
    @Autowired
    ModelMapperConfig modelMapper;

    @PatchMapping("")
    @PreAuthorize("hasRole('ROLE_PM') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseApi> updateUser(@RequestBody UserDTO userDto) {
         try {
             User user =  modelMapper.map(userDto,User.class);
             user.setIs_deleted(false);
             ResponseApi userDTO = service.updateUser(user);
             return new ResponseEntity<>(userDTO, HttpStatus.OK);
         }catch (Exception e){
             throw e;
         }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi> getDetailUser(@NotEmpty @PathVariable("id") UUID id) {
        return ResponseEntity.ok(service.getDetailUser(id));
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
