package com.winnerezy.rae.dto;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

public record BookDTO(@NotBlank(message = "Title cannot be blank") String title, @NotBlank(message = "Description cannot be blank") String description, @Min(value= 1, message = "Stars shout be at least 1") @Max(value = 5, message = "Stars shout be at most 5") @NotNull(message = "Stars cannot be null") double stars, @NotNull MultipartFile image) {
}
