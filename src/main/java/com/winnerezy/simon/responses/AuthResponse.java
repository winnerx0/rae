package com.winnerezy.simon.responses;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class AuthResponse {

    private final String token;

    private LocalDateTime timestamp = LocalDateTime.now();
}
