package com.example.yamp.usersvc.exception;

public class AddressNameConflictException extends RuntimeException {
    public AddressNameConflictException(String message) {
        super("Address name already existed" + message);
    }
}
