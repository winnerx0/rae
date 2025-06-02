package com.winnerezy.rae.responses;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class GeminiErrorResponse {

    private String message;

    private HttpStatus code;

}
