package com.github.ngodat0103.yamp.authsvc.persistence.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {
    @Column(nullable = false,updatable = false)
    private LocalDateTime createAt;
    @Column(nullable = false)
    private LocalDateTime lastModifiedAt;
}
