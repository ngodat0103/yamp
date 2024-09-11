package com.example.yamp.usersvc.dto.customer;

import lombok.*;

import java.util.UUID;

@Data

public class AccountRegisterDto {
    private UUID uuid;
    private String username;
    private String email;
    private String password;
    private String roleName;
}
