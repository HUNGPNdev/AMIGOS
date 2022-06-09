package com.amigos.authentication.controller;

import com.amigos.authentication.jwt.JwtProvider;
import com.amigos.authentication.request.LoginForm;
import com.amigos.authentication.request.SignUpForm;
import com.amigos.authentication.response.JwtResponse;
import com.amigos.common.ResponseApi;
import com.amigos.dto.UserDTO;
import com.amigos.role.repository.RoleRepository;
import com.amigos.user.model.User;
import com.amigos.user.repository.UserRepository;
import com.amigos.user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @PostMapping("/user-details")
    public ResponseEntity<?> getUserByName(@RequestBody LoginForm loginRequest) {
        Optional<User> u = userRepository.findByUserName(loginRequest.getUsername());

        return ResponseEntity.ok(modelMapper.map(u.get(), UserDTO.class));

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {

        ResponseApi userDTO = userService.registerUser(signUpRequest);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
