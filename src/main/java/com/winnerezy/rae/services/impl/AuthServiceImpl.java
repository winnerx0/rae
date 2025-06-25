package com.winnerezy.rae.services.impl;

import com.winnerezy.rae.utils.JwtUtil;
import com.winnerezy.rae.dto.LoginDTO;
import com.winnerezy.rae.dto.RegisterDTO;
import com.winnerezy.rae.exceptions.AuthException;
import com.winnerezy.rae.enums.Role;
import com.winnerezy.rae.models.User;
import com.winnerezy.rae.repositories.UserRepository;
import com.winnerezy.rae.services.AuthService;
import com.winnerezy.rae.services.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProvider authenticationProvider;
    private final UserService userService;

    public AuthServiceImpl(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, AuthenticationProvider authenticationProvider, UserService userService){
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.authenticationProvider = authenticationProvider;
        this.userService = userService;
    }

    @Override
    public String register(RegisterDTO registerDTO){

        if(userRepository.findByUsername(registerDTO.email()).isPresent()){
            throw new AuthException("User with that email exists");
        }

        User user = new User();
        user.setUsername(registerDTO.email());
        user.setName(registerDTO.name());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(registerDTO.password()));
        userRepository.save(user);

        return jwtUtil.generateToken(registerDTO.email(), Role.USER);
    }

    @Override
    public String login(LoginDTO loginDTO){
        authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));
        return jwtUtil.generateToken(loginDTO.email(), Role.USER);
    }

    @Override
    public String verifyToken(String token){

        String username = userService.getCurrentUser().getUsername();
        if(!jwtUtil.validateToken(token, username)){
            throw new BadCredentialsException("Token Invalid");
        }

        return "Token Validated";
    }

}
