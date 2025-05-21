package com.winnerezy.rae.services;

import com.winnerezy.rae.config.JwtUtil;
import com.winnerezy.rae.dto.LoginDTO;
import com.winnerezy.rae.dto.RegisterDTO;
import com.winnerezy.rae.exceptions.NoUserFoundException;
import com.winnerezy.rae.models.User;
import com.winnerezy.rae.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
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

    public ResponseEntity<String> register(RegisterDTO registerDTO){

       try {
           if(userRepository.findByEmail(registerDTO.email()).isPresent()){
               throw new RuntimeException("User with that email exists");
           }

           if(userRepository.findByUsername(registerDTO.username()).isPresent()){
               throw new RuntimeException("User with that username exists");
           }

           User user = new User();
           user.setEmail(registerDTO.email());
           user.setUsername(registerDTO.username());
           user.setPassword(passwordEncoder.encode(registerDTO.password()));
           userRepository.save(user);

           String token = jwtUtil.generateToken(registerDTO.email());
           return ResponseEntity.ok(token);
       } catch(RuntimeException e){
           return ResponseEntity.badRequest().body(e.getMessage());
       }

    }

    public ResponseEntity<String> login(LoginDTO loginDTO){
      try {
          userRepository.findByEmail(loginDTO.email()).orElseThrow(() -> new NoUserFoundException("User Not Found"));

          authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));
          String token = jwtUtil.generateToken(loginDTO.email());
          return ResponseEntity.ok(token);
      } catch(RuntimeException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    }
}
