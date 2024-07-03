package com.example.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    boolean email_verify = false;
    private String first_name;
    private String last_name;
    private String sex;
    private Date birthday;
    private String language;
    private String phone;
    private boolean phone_verify = false;
    private boolean is_active = true;
    private boolean is_superuser = false;
}
