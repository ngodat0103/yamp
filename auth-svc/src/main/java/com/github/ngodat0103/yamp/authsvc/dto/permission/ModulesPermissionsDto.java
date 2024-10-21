package com.github.ngodat0103.yamp.authsvc.dto.permission;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ModulesPermissionsDto {
  private Long id;
  private Long modulesId;
  private Long permissionsId;
}
