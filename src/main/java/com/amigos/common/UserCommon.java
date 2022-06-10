package com.amigos.common;

import com.amigos.authentication.jwt.JwtProvider;
import com.amigos.user.model.User;
import com.amigos.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class UserCommon
{

    public static User getUserFromRequest(HttpServletRequest httpServletRequest, JwtProvider tokenProvider, UserRepository userRepository) {
        String jwt = tokenProvider.getJwt(httpServletRequest);
        String userName = tokenProvider.getUserNameFromJwtToken(jwt);
        Optional<User> user = userRepository.findByUserName(userName);
        if(user.isEmpty()) {
            return null;
        }
        return user.get();
    }
}
