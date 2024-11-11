package com.github.ngodat0103.yamp.authsvc.persistence.entity.role;

import com.github.ngodat0103.yamp.authsvc.persistence.converter.ToUppercaseConverter;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Permission;
import jakarta.persistence.*;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ROLES")
@Getter
@Setter
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, updatable = false)
  @Convert(converter = ToUppercaseConverter.class)
  private String name;

  @ManyToMany
  @JoinTable(
      name = "ROLES_PERMISSIONS",
      joinColumns = @JoinColumn(name = "role_id_fk"),
      inverseJoinColumns = @JoinColumn(name = "permission_id_fk"))
  private Set<Permission> permissions;
}
