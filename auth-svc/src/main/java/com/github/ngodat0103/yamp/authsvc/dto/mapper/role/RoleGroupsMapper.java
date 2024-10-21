package com.github.ngodat0103.yamp.authsvc.dto.mapper.role;

import com.github.ngodat0103.yamp.authsvc.dto.role.RoleGroupsDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.roles.RoleGroups;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleGroupsMapper {
  RoleGroupsMapper INSTANCE = Mappers.getMapper(RoleGroupsMapper.class);

  RoleGroupsDto roleGroupsToRoleGroupsDto(RoleGroups roleGroups);

  RoleGroups roleGroupsDtoToRoleGroups(RoleGroupsDto roleGroupsDto);
}
