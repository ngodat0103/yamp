package com.example.gateway.dto;

public class AccountDto {
    public String username;
    private String password;
    public AccountDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
