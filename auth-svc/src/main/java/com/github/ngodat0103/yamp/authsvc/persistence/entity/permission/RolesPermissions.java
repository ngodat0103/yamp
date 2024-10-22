package com.github.ngodat0103.yamp.authsvc.persistence.entity.permission;

import com.github.ngodat0103.yamp.authsvc.persistence.entity.roles.Role;
import jakarta.persistence.*;

@Entity
@Table(name = "ROLES_PERMISSIONS")
public class RolesPermissions {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "roles_id", nullable = false)
  private Role role;

  @ManyToOne
  @JoinColumn(name = "permissions_id", nullable = false)
  private Permission permission;

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Role getRoles() {
    return role;
  }

  public void setRoles(Role role) {
    this.role = role;
  }

  public Permission getPermissions() {
    return permission;
  }

  public void setPermissions(Permission permission) {
    this.permission = permission;
  }
}
