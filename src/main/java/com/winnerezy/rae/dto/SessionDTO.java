package com.winnerezy.rae.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SessionDTO {

    @NotBlank(message = "Name required")
    private final String name;
}
