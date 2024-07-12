package com.example.userservice.entity;


import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @GeneratedValue( strategy =  GenerationType.AUTO)
    @Id
    private long roleID;
    private String roleName;
    private String roleDescription;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}