package com.winnerezy.rae.services;

import com.winnerezy.rae.dto.LoginDTO;
import com.winnerezy.rae.dto.RegisterDTO;

public interface AuthService {

    String register(RegisterDTO registerDTO);

    String login(LoginDTO loginDTO);

    String verifyToken(String token);
}
