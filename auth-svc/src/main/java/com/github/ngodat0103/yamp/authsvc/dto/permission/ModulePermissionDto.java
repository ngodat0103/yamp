package com.github.ngodat0103.yamp.authsvc.dto.permission;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ModulePermissionDto {

  @NotNull(message = "Module ID is required")
  private Long modulesId;

  @NotNull(message = "Permission ID is required")
  private Long permissionsId;
}
