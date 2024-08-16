package com.example.userservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidInputException extends RuntimeException{

    public InvalidInputException(String message){
        super(message);
    }
    public InvalidInputException(String message, Throwable cause){
        super(message,cause);
    }
}
