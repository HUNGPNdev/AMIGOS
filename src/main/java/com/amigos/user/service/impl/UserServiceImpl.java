package com.amigos.user.service.impl;

import com.amigos.authentication.request.SignUpForm;
import com.amigos.common.ResponseApi;
import com.amigos.dto.UserCartDTO;
import com.amigos.dto.UserDTO;
import com.amigos.errors.handle.ObjectDuplicateException;
import com.amigos.role.model.Role;
import com.amigos.role.model.RoleName;
import com.amigos.role.repository.RoleRepository;
import com.amigos.user.model.User;
import com.amigos.user.repository.UserRepository;
import com.amigos.user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ResponseApi registerUser(SignUpForm signUpRequest) {
        if (userRepository.existsByUserName(signUpRequest.getUserName())) {
            throw new ObjectDuplicateException("Fail -> Username", signUpRequest.getUserName(), signUpRequest);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ObjectDuplicateException("Fail -> Email", signUpRequest.getEmail(), signUpRequest);
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
        User u = userRepository.save(user);
        UserDTO userDTO = modelMapper.map(u, UserDTO.class);
        ResponseApi responseApi = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), userDTO);

        return responseApi;
    }

    @Override
    public ResponseApi findByUserName(String userName)
    {
        Optional<User> u = userRepository.findByUserName(userName);
        UserCartDTO userDTO = modelMapper.map(u.get(), UserCartDTO.class);
        ResponseApi responseApi = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), userDTO);
        return responseApi;
    }
}
