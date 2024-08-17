package com.example.userservice.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"customerUuid", "name"})
})
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    @Column(nullable = false)
    private UUID customerUuid;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String cityName;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String province;

    @Column (nullable = false)
    private String street;
    @Column(nullable = false)
    private String ward;
    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String addressType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerUuid",insertable = false, updatable = false)
   private Customer customer;
}
