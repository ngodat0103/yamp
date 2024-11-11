package com.github.ngodat0103.yamp.authsvc.dto.mapper;

import com.github.ngodat0103.yamp.authsvc.dto.permission.PermissionDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper extends AbstractMapper<PermissionDto, Permission> {}
