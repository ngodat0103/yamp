package org.example.authservice.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid ;
    @Column(nullable = false, unique = true)
    private String username;
    @Column (nullable=false)
    private String password;
    private String email;
//    @OneToMany (mappedBy = "account",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private Set<AccountRole> accountRole;
    public Account(String username,String password){
        this.username = username;
        this.password = password;
    }

}
