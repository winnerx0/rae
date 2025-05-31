package com.winnerezy.rae.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class AIException extends RuntimeException{

    private final HttpStatus status;

    public AIException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }
}
