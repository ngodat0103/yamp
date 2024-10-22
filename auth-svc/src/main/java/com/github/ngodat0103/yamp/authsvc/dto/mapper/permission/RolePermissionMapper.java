package com.github.ngodat0103.yamp.authsvc.dto.mapper.permission;

import com.github.ngodat0103.yamp.authsvc.dto.permission.PermissionDto;
import com.github.ngodat0103.yamp.authsvc.dto.permission.RolePermissionDetailDto;
import com.github.ngodat0103.yamp.authsvc.dto.permission.RolePermissionDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.RolePermission;

public interface RolePermissionMapper {
  RolePermissionDto toDto(RolePermission rolePermission);

  RolePermissionDetailDto toDetailDto(RolePermission rolePermission);

  RolePermission toEntity(RolePermissionDto rolePermissionDto);

  PermissionDto toPermissionDto(RolePermission rolePermission);
}
