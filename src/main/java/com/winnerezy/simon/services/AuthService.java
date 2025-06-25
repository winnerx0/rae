package com.winnerezy.simon.services;

import com.winnerezy.simon.dto.LoginDTO;
import com.winnerezy.simon.dto.RegisterDTO;

public interface AuthService {

    String register(RegisterDTO registerDTO);

    String login(LoginDTO loginDTO);

}
