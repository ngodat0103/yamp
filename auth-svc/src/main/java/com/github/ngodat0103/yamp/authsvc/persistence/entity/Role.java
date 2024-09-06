package com.github.ngodat0103.yamp.authsvc.persistence.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Role  extends BaseEntity{
    @Column(nullable = false, unique = true)
    private String roleName;
    private String roleDescription;

    @OneToMany
    @JoinColumn(name = "roleUuid")
    private Set<Account> accounts ;
}
