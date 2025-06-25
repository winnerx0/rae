package com.winnerezy.simon.controllers;

import com.winnerezy.simon.models.User;
import com.winnerezy.simon.responses.AuthResponse;
import com.winnerezy.simon.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/me")
    public User getUserDetails(){
        return userService.getCurrentUser();
    }

    @GetMapping("/verify-token")
    public ResponseEntity<AuthResponse> verifyToken(@RequestHeader(value = "Authorization") String authorization){
        if(authorization.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthResponse("Token Required"));
        }
        String token = authorization.substring(7);
        return ResponseEntity.ok(new AuthResponse(userService.verifyToken(token)));
    }
}
