package com.winnerezy.chatty.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDTO(@NotBlank(message = "Password cannot be blank")
                       String password,

                       @NotBlank(message = "Email cannot be blank")
                       @Email(message = "Invalid email format")
                       String email) { }
