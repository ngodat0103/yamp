package com.example.userservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @GeneratedValue( strategy =  GenerationType.AUTO)
    @Id
    private long roleID;
    private String roleName;
    private String roleDescription;
}