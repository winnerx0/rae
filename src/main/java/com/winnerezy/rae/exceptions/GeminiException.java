package com.winnerezy.rae.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeminiException extends RuntimeException{

    private final int code;

    public GeminiException(String message, int code){
        super(message);
        this.code = code;
    }

}
