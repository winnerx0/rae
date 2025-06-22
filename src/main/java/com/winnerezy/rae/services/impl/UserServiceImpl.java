package com.winnerezy.rae.services.impl;

import com.winnerezy.rae.exceptions.NoUserFoundException;
import com.winnerezy.rae.exceptions.NoUserFoundException;
import com.winnerezy.rae.models.User;
import com.winnerezy.rae.repositories.UserRepository;
import com.winnerezy.rae.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.atInfo().log("authentication {}", authentication.getName());

        if (!authentication.isAuthenticated()) {
            throw new NoUserFoundException("No authenticated user found");
        }

        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NoUserFoundException("User not found with email: " + username));
    }

}