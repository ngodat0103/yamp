package com.github.ngodat0103.yamp.authsvc.dto.mapper.role;

import com.github.ngodat0103.yamp.authsvc.dto.role.RolesDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.roles.Roles;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RolesMapper {
  RolesMapper INSTANCE = Mappers.getMapper(RolesMapper.class);

  RolesDto rolesToRolesDto(Roles roles);

  Roles rolesDtoToRoles(RolesDto rolesDto);
}
