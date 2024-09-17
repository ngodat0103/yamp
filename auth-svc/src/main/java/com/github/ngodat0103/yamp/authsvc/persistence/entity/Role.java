package com.github.ngodat0103.yamp.authsvc.persistence.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Role  extends BaseEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID uuid;
    @Column(nullable = false, unique = true)
    private String roleName;
    private String roleDescription;

    @OneToMany(mappedBy = "role",fetch = FetchType.LAZY)
    private Set<Account> accounts ;
}
