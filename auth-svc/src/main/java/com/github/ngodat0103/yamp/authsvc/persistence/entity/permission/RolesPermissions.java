package com.github.ngodat0103.yamp.authsvc.persistence.entity.permission;

import com.github.ngodat0103.yamp.authsvc.persistence.entity.roles.Roles;
import jakarta.persistence.*;

@Entity
@Table(name = "ROLES_PERMISSIONS")
public class RolesPermissions {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "roles_id", nullable = false)
  private Roles roles;

  @ManyToOne
  @JoinColumn(name = "permissions_id", nullable = false)
  private Permissions permissions;

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Roles getRoles() {
    return roles;
  }

  public void setRoles(Roles roles) {
    this.roles = roles;
  }

  public Permissions getPermissions() {
    return permissions;
  }

  public void setPermissions(Permissions permissions) {
    this.permissions = permissions;
  }
}
