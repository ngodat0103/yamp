package com.example.yamp.usersvc.exception;

public abstract class NotFoundException extends RuntimeException{
    protected NotFoundException(String message) {
        super(message);
    }
}
