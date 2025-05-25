package com.winnerezy.rae.dto;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

public record BookDTO(@NotBlank(message = "title cannot be blank") String title,
                      @NotBlank(message = "description cannot be blank") String description,
                      @Min(value= 1, message = "stars shout be at least 1") @Max(value = 5, message = "stars shout be at most 5") @NotNull(message = "stars cannot be null") Double stars,
                      @NotNull(message = "image required") MultipartFile image) {
}
