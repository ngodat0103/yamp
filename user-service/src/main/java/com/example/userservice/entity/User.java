package com.example.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "_User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;
    @Column(unique = true,nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
    boolean email_verify = false;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    private String sex;
    private Date birthday;
    private String language;
    private String phone;
    private boolean phone_verify = false;
    private boolean is_active = true;
    private boolean is_superuser = false;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    Set<Role> roles;

}
