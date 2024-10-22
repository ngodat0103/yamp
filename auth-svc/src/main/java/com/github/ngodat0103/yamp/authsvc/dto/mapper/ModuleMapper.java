package com.github.ngodat0103.yamp.authsvc.dto.mapper;

import com.github.ngodat0103.yamp.authsvc.dto.ModuleDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Module;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ModuleMapper {
  ModuleMapper INSTANCE = Mappers.getMapper(ModuleMapper.class);

  ModuleDto toDto(Module module);

  Module toEntity(ModuleDto moduleDto);
}
