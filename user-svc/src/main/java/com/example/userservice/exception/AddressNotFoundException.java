package com.example.userservice.exception;

import java.util.UUID;

public class AddressNotFoundException extends NotFoundException{
    public AddressNotFoundException(UUID addresUuid) {
        super("Address not found for UUID: " + addresUuid.toString());
    }
}
