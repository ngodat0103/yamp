package com.github.ngodat0103.yamp.authsvc.dto.mapper;

import com.github.ngodat0103.yamp.authsvc.dto.role.RoleDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.role.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper extends AbstractMapper<RoleDto, Role> {}
