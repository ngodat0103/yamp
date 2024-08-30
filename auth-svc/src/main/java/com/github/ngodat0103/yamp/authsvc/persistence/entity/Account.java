package com.github.ngodat0103.yamp.authsvc.persistence.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    private UUID accountUuid;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column (nullable=false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "roleUuid")
    private Role role;

    public Account(UUID accountUuid ,String email,String username, String password){
        this.username = username;
        this.password = password;
        this.accountUuid = accountUuid;
        this.email = email;
    }

}