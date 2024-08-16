package com.example.userservice.exception;

public class AddressNameConflictException extends RuntimeException {
    public AddressNameConflictException(String message) {
        super("Address name already existed" + message);
    }
}
