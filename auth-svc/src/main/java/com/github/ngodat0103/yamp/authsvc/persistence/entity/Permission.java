package com.github.ngodat0103.yamp.authsvc.persistence.entity;

import com.github.ngodat0103.yamp.authsvc.persistence.converter.ToUppercaseConverter;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.role.Role;
import jakarta.persistence.*;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PERMISSIONS")
@Getter
@Setter
public class Permission {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Convert(converter = ToUppercaseConverter.class)
  @Column(nullable = false)
  private String name;

  @ManyToMany(mappedBy = "permissions")
  private Set<Module> modules;

  @ManyToMany(mappedBy = "permissions")
  private Set<Role> roles;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "PERMISSIONS_OPERATIONS",
      joinColumns = @JoinColumn(name = "permission_id_fk"),
      inverseJoinColumns = @JoinColumn(name = "operation_id_fk"))
  private Set<Operation> operations;
}
