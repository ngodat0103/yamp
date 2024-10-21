package com.github.ngodat0103.yamp.authsvc.dto.mapper.permission;

import com.github.ngodat0103.yamp.authsvc.dto.permission.PermissionsOperationsDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.PermissionsOperations;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PermissionsOperationsMapper {
  PermissionsOperationsMapper INSTANCE = Mappers.getMapper(PermissionsOperationsMapper.class);

  PermissionsOperationsDto permissionsOperationsToPermissionsOperationsDto(
      PermissionsOperations permissionsOperations);

  PermissionsOperations permissionsOperationsDtoToPermissionsOperations(
      PermissionsOperationsDto permissionsOperationsDto);
}
