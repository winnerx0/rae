package com.winnerezy.simon.services.impl;

import com.winnerezy.simon.utils.JwtUtil;
import com.winnerezy.simon.dto.LoginDTO;
import com.winnerezy.simon.dto.RegisterDTO;
import com.winnerezy.simon.exceptions.AuthException;
import com.winnerezy.simon.enums.Role;
import com.winnerezy.simon.models.User;
import com.winnerezy.simon.repositories.UserRepository;
import com.winnerezy.simon.services.AuthService;
import com.winnerezy.simon.services.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
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

}
