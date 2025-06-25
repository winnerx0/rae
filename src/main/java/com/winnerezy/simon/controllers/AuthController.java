package com.winnerezy.simon.controllers;

import com.winnerezy.simon.dto.LoginDTO;
import com.winnerezy.simon.dto.RegisterDTO;
import com.winnerezy.simon.responses.AuthResponse;
import com.winnerezy.simon.services.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
}
