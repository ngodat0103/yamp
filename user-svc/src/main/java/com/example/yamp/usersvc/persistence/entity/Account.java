package com.example.yamp.usersvc.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@RedisHash("Account")
@NoArgsConstructor
public class Account {
    @Id
    private UUID uuid;
    private String username;
    private String email;
    private LocalDateTime createAt;
    private LocalDateTime lastModifiedAt;
}
