package com.example.userservice.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
public abstract class BillingDetail   {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @NotNull
    private UUID customerUuid;

    private LocalDateTime createAtDate;
    private LocalDateTime lastModifiedDate;


    @ManyToOne
    @JoinColumn(name = "customerUuid", insertable = false, updatable = false)
    Customer customer;


}
