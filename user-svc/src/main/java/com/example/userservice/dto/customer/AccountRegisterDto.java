package com.example.userservice.dto.customer;

import lombok.*;

import java.util.UUID;

@Data

public class AccountRegisterDto {
    private String username;
    private String email;
    private String password;
    private UUID accountUuid;
}
