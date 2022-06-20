package com.amigos.user.service;

import com.amigos.authentication.request.SignUpForm;
import com.amigos.common.ResponseApi;
import com.amigos.user.model.User;

import java.util.UUID;

public interface UserService {
    ResponseApi registerUser(SignUpForm signUpRequest);

    ResponseApi updateUser(User user);

    ResponseApi getDetailUser(UUID id);

    ResponseApi delete(UUID id);

    ResponseApi getAll();
}
