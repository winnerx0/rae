package com.winnerezy.simon.exceptions;

public class NoUserFoundException extends RuntimeException{

   public NoUserFoundException(String message){
       super(message);
   }
}
