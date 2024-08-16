package org.example.authservice.persistence.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.authservice.persistence.converter.RoleConverter;

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
    @Convert(converter = RoleConverter.class)
    private Set<String> roles;

    public Account(UUID accountUuid ,String email,String username, String password){
        this.username = username;
        this.password = password;
        this.accountUuid = accountUuid;
        this.email = email;
    }

}