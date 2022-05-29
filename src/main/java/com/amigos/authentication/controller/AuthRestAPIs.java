package com.amigos.authentication.controller;

import com.amigos.authentication.jwt.JwtProvider;
import com.amigos.authentication.request.LoginForm;
import com.amigos.authentication.request.SignUpForm;
import com.amigos.authentication.response.JwtResponse;
import com.amigos.authentication.response.ResponseMessage;
import com.amigos.dto.UserDTO;
import com.amigos.role.model.Role;
import com.amigos.role.model.RoleName;
import com.amigos.role.repository.RoleRepository;
import com.amigos.user.model.User;
import com.amigos.user.repository.UserRepository;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @PostMapping("/role")
    public String createRole() {
        Role role1 = new Role();
        role1.setName(RoleName.ROLE_PM);
        roleRepository.save(role1);
        return ">>> Ok";

    }

    @PostMapping("/user")
    public String createUser(@RequestBody User user) {
        userRepository.save(user);
        return ">>> Ok";

    }

    @PostMapping("/user-details")
    public ResponseEntity<?> getUserByName(@RequestBody LoginForm loginRequest) {
        Optional<User> u = userRepository.findByUserName(loginRequest.getUsername());

        return ResponseEntity.ok(modelMapper.map(u.get(), UserDTO.class));

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
        if (userRepository.existsByUserName(signUpRequest.getUserName())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getUserName(), signUpRequest.getFirstName(), signUpRequest.getLastName(),
                signUpRequest.getEmail(), signUpRequest.getPhone(), signUpRequest.getAddress(),
                encoder.encode(signUpRequest.getPassword()), false);
        user.setCreate_at(new Date());

        List<String> strRoles = signUpRequest.getRole();
        List<Role> roles = new ArrayList<>();

        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(adminRole);

                    break;
                case "pm":
                    Role pmRole = roleRepository.findByName(RoleName.ROLE_PM)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(pmRole);

                    break;
                default:
                    Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(userRole);
            }
        });

        user.setRoles(roles);
        userRepository.save(user);

        return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
    }
}
