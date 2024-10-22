package com.github.ngodat0103.yamp.authsvc.dto.permission;

import com.github.ngodat0103.yamp.authsvc.dto.role.RoleDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RolePermissionDetailDto {
  private Long id;
  private RoleDto roleDto;
  private PermissionDto permissionDto;
}
