package com.github.ngodat0103.yamp.authsvc.dto.permission;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RolePermissionDto {
  @NotNull(message = "roleId is required")
  private Long roleId;

  @NotNull(message = "permissionId is required")
  private Long permissionId;
}
