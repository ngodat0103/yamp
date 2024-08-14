package com.example.userservice.dto.customer;

import lombok.*;

import java.util.UUID;

@Data
public class AccountDto {
    private UUID accountUuid;
    private String username;
    private String email;
    private String password;
}
