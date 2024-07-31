package com.example.userservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    private UUID accountUuid;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String phoneNumber;
    @OneToMany (mappedBy = "accountUuid")
    private Set<Address> addresses;
}
