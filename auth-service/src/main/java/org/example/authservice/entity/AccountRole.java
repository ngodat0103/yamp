package org.example.authservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@IdClass(AccountRole.class)
public class AccountRole implements Serializable {
    @Id
    private String username;
    @Id
    private String roleName;
//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "username", insertable = false, updatable = false, referencedColumnName = "username")
//    Account account;
//
//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "roleName", insertable = false, updatable = false, referencedColumnName = "roleName")
//    Role role;
    public AccountRole(String username, String roleName) {
        this.username = username;
        this.roleName = roleName;
    }
}
