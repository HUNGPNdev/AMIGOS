package com.amigos.user.service.impl;

import com.amigos.authentication.request.SignUpForm;
import com.amigos.common.ResponseApi;
import com.amigos.dto.OrderCartDTO;
import com.amigos.config.ModelMapperConfig;
import com.amigos.dto.UserDTO;
import com.amigos.dto.UserInputDto;
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
import java.util.*;

import static com.amigos.common.Constants.ENTITY_NOT_FOUND;

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

    @Autowired
    ModelMapperConfig modelMapperConfig;

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
        OrderCartDTO userDTO = modelMapper.map(u.get(), OrderCartDTO.class);
        ResponseApi responseApi = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), userDTO);
        return responseApi;
    }

    @Override
    public ResponseApi updateUser(User user) {
        Optional<User> find = userRepository.findById(user.getId());
        if (!find.isEmpty()) {
            if (userRepository.existsByUserName(user.getUserName())
                    && !find.get().getUserName().equals(user.getUserName())) {
                throw new ObjectDuplicateException("Username", user.getUserName(), user);
            }

            if (userRepository.existsByEmail(user.getEmail())
                    && !find.get().getEmail().equals(user.getEmail())) {
                throw new ObjectDuplicateException("Email", user.getEmail(), user);
            }
            String password = encoder.encode(user.getPassword());
            user.setPassword(password);
            user.setUpdate_at(new Date());
            User userEntity = userRepository.save(user);
            UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);
            ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), userDTO);
            return rs;
        } else {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
    }

    @Override
    public ResponseApi getDetailUser(UUID id) {
        Optional<User> userEntity = userRepository.findById(id);
        if(!userEntity.isEmpty()) {
            UserInputDto map = modelMapperConfig.map(userEntity.get(), UserInputDto.class);
            ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), map);
            return rs;
        } else {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
    }

    @Override
    public ResponseApi delete(UUID id) {
        Optional<User> userEntity = userRepository.findById(id);
        if(!userEntity.isEmpty()) {
            User entity = userEntity.get();
            entity.setIs_deleted(true);
            userRepository.save(entity);
            UserDTO map = modelMapperConfig.map(entity, UserDTO.class);
            ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), map);
            return rs;
        } else {
            ResponseApi rs = new ResponseApi(HttpStatus.NOT_FOUND.value(), ENTITY_NOT_FOUND);
            return rs;
        }
    }

    @Override
    public ResponseApi getAll() {
        List<UserInputDto> userEntity = userRepository.getAll(Boolean.FALSE);
        ResponseApi rs = new ResponseApi(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), modelMapperConfig.mapAll(userEntity, UserInputDto.class));
        return rs;
    }
}
