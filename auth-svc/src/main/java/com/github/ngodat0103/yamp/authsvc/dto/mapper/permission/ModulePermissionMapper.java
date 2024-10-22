package com.github.ngodat0103.yamp.authsvc.dto.mapper.permission;

import com.github.ngodat0103.yamp.authsvc.dto.permission.ModulePermissionDetailDto;
import com.github.ngodat0103.yamp.authsvc.dto.permission.ModulePermissionDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.ModulePermission;

public interface ModulePermissionMapper {
  ModulePermissionDto toDto(ModulePermission modulePermission);

  ModulePermissionDetailDto toDetailDto(ModulePermission modulePermission);

  ModulePermission toEntity(ModulePermissionDto modulePermissionDto);
}
