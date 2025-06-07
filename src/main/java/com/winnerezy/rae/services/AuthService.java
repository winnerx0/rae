package com.winnerezy.rae.services;

import com.winnerezy.rae.config.JwtUtil;
import com.winnerezy.rae.dto.LoginDTO;
import com.winnerezy.rae.dto.RegisterDTO;
import com.winnerezy.rae.exceptions.AuthException;
import com.winnerezy.rae.enums.Role;
import com.winnerezy.rae.models.User;
import com.winnerezy.rae.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProvider authenticationProvider;
    private final UserService userService;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, AuthenticationProvider authenticationProvider, UserService userService){
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.authenticationProvider = authenticationProvider;
        this.userService = userService;
    }

    public String register(RegisterDTO registerDTO){

           if(userRepository.findByUsername(registerDTO.email()).isPresent()){
               throw new AuthException("User with that email exists");
           }

           User user = new User();
           user.setUsername(registerDTO.email());
           user.setName(registerDTO.name());
           user.setRole(Role.ADMIN);
           user.setPassword(passwordEncoder.encode(registerDTO.password()));
           userRepository.save(user);

           return jwtUtil.generateToken(registerDTO.email(), Role.USER);
    }

    public String login(LoginDTO loginDTO){
        authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));
        return jwtUtil.generateToken(loginDTO.email(), Role.USER);
    }

    public String verifyToken(String token){

        String username = userService.getCurrentUser().getUsername();
       if(!jwtUtil.validateToken(token, username)){
           throw new BadCredentialsException("Token Invalid");
       }

       return "Token Validated";
    }
}
