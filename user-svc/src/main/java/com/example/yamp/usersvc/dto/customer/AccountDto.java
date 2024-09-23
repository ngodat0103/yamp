package com.example.yamp.usersvc.dto.customer;

import java.util.UUID;

public record AccountDto(UUID uuid, String username, String email) {}
