package com.github.ngodat0103.yamp.authsvc.dto.mapper.role;

import com.github.ngodat0103.yamp.authsvc.dto.permission.RolesPermissionsDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.RolesPermissions;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RolesPermissionsMapper {
  RolesPermissionsMapper INSTANCE = Mappers.getMapper(RolesPermissionsMapper.class);

  RolesPermissionsDto rolesPermissionsToRolesPermissionsDto(RolesPermissions rolesPermissions);

  RolesPermissions rolesPermissionsDtoToRolesPermissions(RolesPermissionsDto rolesPermissionsDto);
}
