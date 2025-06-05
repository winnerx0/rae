package com.winnerezy.rae.controllers;

import com.winnerezy.rae.models.User;
import com.winnerezy.rae.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")
    public User getUserDetails(){
        return userService.getCurrentUser();
    }
}
