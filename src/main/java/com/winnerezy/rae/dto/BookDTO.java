package com.winnerezy.rae.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record BookDTO(@NotBlank(message = "Title cannot be blank") String title, @NotBlank(message = "Description cannot be blank") String description, @NotBlank(message = "Stars cannot be blank") @Size(min = 1, max = 5) int stars, @NotNull MultipartFile image) {
}
