package com.winnerezy.chatty.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record BookDTO(@NotBlank String title, @NotBlank String description, @NotNull MultipartFile image) {
}
