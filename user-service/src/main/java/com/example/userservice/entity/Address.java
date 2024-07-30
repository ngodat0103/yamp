package com.example.userservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID uuid;
    private UUID accountUuid;
    private String name;
    private String cityName;
    private String phoneNumber;
    private String province;
    private String district;
    private String ward;
    private String addressType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "accountUuid",insertable = false, updatable = false)
   private Customer customer;
}
