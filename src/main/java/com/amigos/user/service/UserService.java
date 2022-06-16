package com.amigos.user.service;

import com.amigos.authentication.request.SignUpForm;
import com.amigos.common.ResponseApi;

public interface UserService {
    ResponseApi registerUser(SignUpForm signUpRequest);
    ResponseApi findByUserName(String userName);
}
