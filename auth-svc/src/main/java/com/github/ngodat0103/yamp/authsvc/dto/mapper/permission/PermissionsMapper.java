package com.github.ngodat0103.yamp.authsvc.dto.mapper.permission;

import com.github.ngodat0103.yamp.authsvc.dto.permission.PermissionsDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.Permissions;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PermissionsMapper {
  PermissionsMapper INSTANCE = Mappers.getMapper(PermissionsMapper.class);

  PermissionsDto permissionsToPermissionsDto(Permissions permissions);

  Permissions permissionsDtoToPermissions(PermissionsDto permissionsDto);
}
