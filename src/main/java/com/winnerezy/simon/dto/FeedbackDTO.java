package com.winnerezy.simon.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackDTO {

    @NotBlank(message = "Message required")
    private String message;

    @NotNull(message = "Stars required")
    @Min(value = 1, message = "Minimum of 1 star required")
    @Max(value = 5, message = "Maximum of 5 stars")
    private int stars;
}
