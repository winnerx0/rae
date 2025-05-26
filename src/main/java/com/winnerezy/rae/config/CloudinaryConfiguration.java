package com.winnerezy.rae.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix = "cloudinary")
@Getter
@Setter
@Validated
public class CloudinaryConfiguration {

    @NotEmpty(message = "API key cannot be empty")
    private String apiKey;

    @NotEmpty(message = "Secret key cannot be empty")
    private String secretKey;

    @NotEmpty(message = "Cloud name cannot be empty")
    private String cloudName;

    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", secretKey,
                "secure", true));
    }
}
