package com.example.yamp.usersvc.dto.kafka;


import java.time.LocalDateTime;

public record AccountMessingDto(Action action, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
}
