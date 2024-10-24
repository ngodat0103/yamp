package com.github.ngodat0103.yamp.authsvc.dto.permission;

import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RolePermissionsDto {
  private Long id;
  private String name;
  private Set<PermissionDto> permissions;
}
