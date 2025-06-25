package com.winnerezy.simon.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MessageDTO {

    @NotBlank(message = "Message required")
    private String message;
}
