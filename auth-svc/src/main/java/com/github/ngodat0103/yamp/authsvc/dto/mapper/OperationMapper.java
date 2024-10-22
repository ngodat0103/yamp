package com.github.ngodat0103.yamp.authsvc.dto.mapper;

import com.github.ngodat0103.yamp.authsvc.dto.OperationDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OperationMapper {
  OperationMapper INSTANCE = Mappers.getMapper(OperationMapper.class);

  OperationDto toDto(Operation operation);

  Operation toEntity(OperationDto operationDto);
}
