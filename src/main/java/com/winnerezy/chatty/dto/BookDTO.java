package com.winnerezy.chatty.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record BookDTO(@NotBlank(message = "Title cannot be blank") String title, @NotBlank(message = "Description cannot be blank") String description, @NotNull MultipartFile image) {
}
