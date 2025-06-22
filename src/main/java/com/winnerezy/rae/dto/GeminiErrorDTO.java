package com.winnerezy.rae.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeminiErrorDTO {
    private Error error;

    @Getter
    @Setter
    public static class Error {
        private int code;
        private String message;
        private String status;
    }
}
