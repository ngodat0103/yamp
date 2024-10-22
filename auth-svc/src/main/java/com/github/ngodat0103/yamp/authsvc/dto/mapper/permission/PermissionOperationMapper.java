package com.github.ngodat0103.yamp.authsvc.dto.mapper.permission;

import com.github.ngodat0103.yamp.authsvc.dto.permission.PermissionOperationDetailDto;
import com.github.ngodat0103.yamp.authsvc.dto.permission.PermissionOperationDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.PermissionOperation;

public interface PermissionOperationMapper {
  PermissionOperationDto toDto(PermissionOperation permissionOperation);

  PermissionOperationDetailDto toDetailDto(PermissionOperation permissionOperation);

  PermissionOperation toEntity(PermissionOperationDto permissionOperationDto);
}
