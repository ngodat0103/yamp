package com.github.ngodat0103.yamp.authsvc.dto.mapper.role;

import com.github.ngodat0103.yamp.authsvc.dto.role.RoleDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.role.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
  RoleDto toDto(Role role);

  Role toEntity(RoleDto roleDto);
}
