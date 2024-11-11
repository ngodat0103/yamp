package com.github.ngodat0103.yamp.authsvc.dto.role;

public class RoleGroupsRolesDto {
  private Long id;
  private Long roleGroupsId;
  private Long roleId;

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getRoleGroupsId() {
    return roleGroupsId;
  }

  public void setRoleGroupsId(Long roleGroupsId) {
    this.roleGroupsId = roleGroupsId;
  }

  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }
}
