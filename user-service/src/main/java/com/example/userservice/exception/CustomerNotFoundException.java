package com.example.userservice.exception;

import org.aspectj.weaver.ast.Not;

import java.util.UUID;

public class CustomerNotFoundException extends NotFoundException {
    public CustomerNotFoundException(UUID customerUuid) {
        super("Customer not found for UUID: " + customerUuid.toString());
    }
}
