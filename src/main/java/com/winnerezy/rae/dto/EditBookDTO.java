package com.winnerezy.rae.dto;

import org.springframework.web.multipart.MultipartFile;

public record EditBookDTO(String title, String description, MultipartFile image) {
}
