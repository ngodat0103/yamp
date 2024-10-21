package com.github.ngodat0103.yamp.authsvc.dto.permission;

public class PermissionsOperationsDto {
  private Long id;
  private Long permissionId;
  private Long operationsId;

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getPermissionId() {
    return permissionId;
  }

  public void setPermissionId(Long permissionId) {
    this.permissionId = permissionId;
  }

  public Long getOperationsId() {
    return operationsId;
  }

  public void setOperationsId(Long operationsId) {
    this.operationsId = operationsId;
  }
}
