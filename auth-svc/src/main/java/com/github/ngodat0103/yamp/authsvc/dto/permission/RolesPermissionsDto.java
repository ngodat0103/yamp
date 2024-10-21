package com.github.ngodat0103.yamp.authsvc.dto.permission;

public class RolesPermissionsDto {
  private Long id;
  private Long rolesId;
  private Long permissionsId;

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getRolesId() {
    return rolesId;
  }

  public void setRolesId(Long rolesId) {
    this.rolesId = rolesId;
  }

  public Long getPermissionsId() {
    return permissionsId;
  }

  public void setPermissionsId(Long permissionsId) {
    this.permissionsId = permissionsId;
  }
}
