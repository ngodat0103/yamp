package com.example.userservice.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Customer  {
    @Id
    private UUID customerUuid;
    @Column(unique = true)
    private UUID accountUuid;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String phoneNumber;
    @OneToMany(mappedBy = "customer")
    private Set<Address> addresses;
    private LocalDateTime createAtDate;
    private LocalDateTime lastModifiedDate;

}