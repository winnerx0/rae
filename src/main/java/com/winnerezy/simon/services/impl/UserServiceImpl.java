package com.winnerezy.simon.services.impl;

import com.winnerezy.simon.exceptions.NoUserFoundException;
import com.winnerezy.simon.models.User;
import com.winnerezy.simon.repositories.UserRepository;
import com.winnerezy.simon.services.UserService;
import com.winnerezy.simon.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, JwtUtil jwtUtil){
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.atInfo().log("authentication {}", authentication.getName());

        if (!authentication.isAuthenticated()) {
            throw new NoUserFoundException("No authenticated user found");
        }

        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NoUserFoundException("User not found with email: " + username));
    }

    @Override
    public String verifyToken(String token){

        String username = getCurrentUser().getUsername();
        if(!jwtUtil.validateToken(token, username)){
            throw new BadCredentialsException("Token Invalid");
        }

        return "Token Validated";
    }

}