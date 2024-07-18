package org.example.authservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class AccountRole {
    @Id
    private UUID accountId;
    @Id
    private String roleName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "accountId", insertable = false, updatable = false, referencedColumnName = "uuid")
    Account account;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "roleName", insertable = false, updatable = false, referencedColumnName = "roleName")
    Role role;
}
