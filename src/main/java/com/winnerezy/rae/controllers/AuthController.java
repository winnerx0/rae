package com.winnerezy.rae.controllers;

import com.winnerezy.rae.dto.LoginDTO;
import com.winnerezy.rae.dto.RegisterDTO;
import com.winnerezy.rae.responses.AuthResponse;
import com.winnerezy.rae.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterDTO registerDTO){
        return ResponseEntity.ok(new AuthResponse(authService.register(registerDTO)));

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginDTO loginDTO){
        return ResponseEntity.ok(new AuthResponse(authService.login(loginDTO)));
    }
}
