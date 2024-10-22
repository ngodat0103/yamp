package com.github.ngodat0103.yamp.authsvc.dto.mapper.role;

import com.github.ngodat0103.yamp.authsvc.dto.role.RoleGroupsRolesDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.role.RoleGroupsRoles;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleGroupsRolesMapper {
  RoleGroupsRolesMapper INSTANCE = Mappers.getMapper(RoleGroupsRolesMapper.class);

  RoleGroupsRolesDto roleGroupsRolesToRoleGroupsRolesDto(RoleGroupsRoles roleGroupsRoles);

  RoleGroupsRoles roleGroupsRolesDtoToRoleGroupsRoles(RoleGroupsRolesDto roleGroupsRolesDto);
}
