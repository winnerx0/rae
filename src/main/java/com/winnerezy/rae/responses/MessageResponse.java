package com.winnerezy.rae.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

public class MessageResponse extends SuperResponse {

    public  MessageResponse(String message){
        super(message);
    }
}
