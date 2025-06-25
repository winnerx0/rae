package com.winnerezy.simon.services;

import com.winnerezy.simon.models.User;

public interface UserService {

    User getCurrentUser();

    String verifyToken(String token);

}
