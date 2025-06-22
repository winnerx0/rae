package com.winnerezy.rae.controllers;

import com.winnerezy.rae.dto.LoginDTO;
import com.winnerezy.rae.dto.RegisterDTO;
import com.winnerezy.rae.responses.AuthResponse;
import com.winnerezy.rae.services.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterDTO registerDTO){
        log.info("user {}", registerDTO.email());
        return ResponseEntity.ok(new AuthResponse(authService.register(registerDTO)));

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginDTO loginDTO){
        return ResponseEntity.ok(new AuthResponse(authService.login(loginDTO)));
    }

    @GetMapping("/verify-token")
    public ResponseEntity<AuthResponse> verifyToken(@RequestHeader(value = "Authorization") String authorization){
        if(authorization.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthResponse("Token Required"));
        }
        String token = authorization.substring(7);
        return ResponseEntity.ok(new AuthResponse(authService.verifyToken(token)));
    }
}
