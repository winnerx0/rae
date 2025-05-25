package com.winnerezy.rae.services;

import com.winnerezy.rae.config.JwtUtil;
import com.winnerezy.rae.dto.LoginDTO;
import com.winnerezy.rae.dto.RegisterDTO;
import com.winnerezy.rae.exceptions.AuthException;
import com.winnerezy.rae.exceptions.NoUserFoundException;
import com.winnerezy.rae.models.User;
import com.winnerezy.rae.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager){
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public String register(RegisterDTO registerDTO){

       try {
           if(userRepository.findByUsername(registerDTO.email()).isPresent()){
               throw new AuthException("User with that email exists");
           }

           if(userRepository.findByName(registerDTO.name()).isPresent()){
               throw new AuthException("User with that username exists");
           }

           User user = new User();
           user.setUsername(registerDTO.email());
           user.setName(registerDTO.name());
           user.setPassword(passwordEncoder.encode(registerDTO.password()));
           userRepository.save(user);

           return jwtUtil.generateToken(registerDTO.email());
       } catch(AuthException e){
           return e.getMessage();
       }

    }

    public String login(LoginDTO loginDTO){
      try {
          authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));
          return jwtUtil.generateToken(loginDTO.email());
      } catch(AuthenticationException e){
          return e.getMessage();
    }
    }
}
