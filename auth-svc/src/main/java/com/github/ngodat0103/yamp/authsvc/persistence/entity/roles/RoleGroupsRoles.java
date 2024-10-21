package com.github.ngodat0103.yamp.authsvc.persistence.entity.roles;

import jakarta.persistence.*;

@Entity
@Table(name = "ROLE_GROUPS_ROLES")
public class RoleGroupsRoles {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "role_groups_id", nullable = false)
  private RoleGroups roleGroups;

  @ManyToOne
  @JoinColumn(name = "role_id", nullable = false)
  private Roles role;

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public RoleGroups getRoleGroups() {
    return roleGroups;
  }

  public void setRoleGroups(RoleGroups roleGroups) {
    this.roleGroups = roleGroups;
  }

  public Roles getRole() {
    return role;
  }

  public void setRole(Roles role) {
    this.role = role;
  }
}
