package com.winnerezy.rae.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MessageDTO {

    @NotBlank(message = "Message required")
    private String message;
}
