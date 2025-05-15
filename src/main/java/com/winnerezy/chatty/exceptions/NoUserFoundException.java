package com.winnerezy.chatty.exceptions;

public class NoUserFoundException extends RuntimeException{

   public NoUserFoundException(String message){
       super(message);
   }
}
