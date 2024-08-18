package com.example.yamp.usersvc.exception;

import java.util.UUID;

public class CustomerNotFoundException extends NotFoundException {
    public CustomerNotFoundException(UUID customerUuid) {
        super("Customer not found for UUID: " + customerUuid.toString());
    }
}
