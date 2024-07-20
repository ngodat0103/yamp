package org.example.authservice.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@NoArgsConstructor
public class Account{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID accountUuid;
    @Column(nullable = false, unique = true)
    @Getter
    @Setter
    private String username;
    @Column (nullable=false)
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String email;
    @OneToMany (mappedBy = "account",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<AccountRole> accountRole;

    public Set<AccountRole> getAccountRole (){
        return Collections.unmodifiableSet(accountRole);
    }



    public Account(String username,String password){
        this.username = username;
        this.password = password;
    }

}
