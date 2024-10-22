package com.github.ngodat0103.yamp.authsvc.dto.mapper.permission;

import com.github.ngodat0103.yamp.authsvc.dto.permission.PermissionDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
  PermissionMapper INSTANCE = Mappers.getMapper(PermissionMapper.class);

  PermissionDto toDto(Permission permission);

  Permission toEntity(PermissionDto permissionDto);
}
