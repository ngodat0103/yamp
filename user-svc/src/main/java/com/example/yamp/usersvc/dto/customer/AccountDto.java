package com.example.yamp.usersvc.dto.customer;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public record AccountDto (UUID uuid,String username,String email)
{}
