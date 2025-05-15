package com.winnerezy.chatty.services;

import com.winnerezy.chatty.config.JwtUtil;
import com.winnerezy.chatty.dto.LoginDTO;
import com.winnerezy.chatty.dto.RegisterDTO;
import com.winnerezy.chatty.exceptions.NoUserFoundException;
import com.winnerezy.chatty.models.User;
import com.winnerezy.chatty.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
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
          User user = userRepository.findByEmail(loginDTO.email()).orElseThrow(() -> new NoUserFoundException("No user found"));

          if(!passwordEncoder.matches(loginDTO.password(), user.getPassword())){
              throw new RuntimeException("Password is incorrect");
          }

          String token = jwtUtil.generateToken(loginDTO.email());
          return ResponseEntity.ok(token);
      } catch(RuntimeException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    }
}
