package com.github.ngodat0103.yamp.authsvc.dto.mapper.permission;

import com.github.ngodat0103.yamp.authsvc.dto.permission.OperationsDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.module.Operations;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OperationsMapper {
  OperationsMapper INSTANCE = Mappers.getMapper(OperationsMapper.class);

  OperationsDto operationsToOperationsDto(Operations operations);

  Operations operationsDtoToOperations(OperationsDto operationsDto);
}
