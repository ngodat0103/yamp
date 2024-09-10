package com.github.ngodat0103.yamp.productsvc.persistence.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false )
    private UUID uuid;
    @Column(nullable = false,updatable = false)
    private UUID createdBy;
    @Column(nullable = false)
    private UUID lastModifiedBy;
    @Column(nullable = false)
    @Setter(AccessLevel.PRIVATE)
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(nullable = false)
    private LocalDateTime lastModifiedAt;


    public BaseEntity(UUID createdBy) {
        this.createdBy = createdBy;
        this.lastModifiedBy = createdBy;
        this.createdAt = LocalDateTime.now();
        this.lastModifiedAt = createdAt;
    }
}
