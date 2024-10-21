package com.github.ngodat0103.yamp.authsvc.dto.mapper.permission;

import com.github.ngodat0103.yamp.authsvc.dto.permission.ModulesPermissionsDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.ModulesPermissions;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ModulesPermissionsMapper {
  ModulesPermissionsMapper INSTANCE = Mappers.getMapper(ModulesPermissionsMapper.class);

  ModulesPermissionsDto modulesPermissionsToModulesPermissionsDto(
      ModulesPermissions modulesPermissions);

  ModulesPermissions modulesPermissionsDtoToModulesPermissions(
      ModulesPermissionsDto modulesPermissionsDto);
}
