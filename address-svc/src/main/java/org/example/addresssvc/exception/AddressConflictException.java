package org.example.addresssvc.exception;

public class AddressConflictException extends RuntimeException {
    public AddressConflictException(String message) {
        super(message);
    }
}
