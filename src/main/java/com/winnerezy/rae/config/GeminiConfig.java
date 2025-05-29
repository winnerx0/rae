package com.winnerezy.rae.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("gemini")
@Data
public class GeminiConfig {

    private String apiKey;
}
