package com.example.yamp.usersvc.dto.kafka;

import java.time.LocalDateTime;

public record AccountTopicContent(
    String firstName,
    String lastName,
    String username,
    String email,
    LocalDateTime lastModifiedAt,
    Action action) {}
