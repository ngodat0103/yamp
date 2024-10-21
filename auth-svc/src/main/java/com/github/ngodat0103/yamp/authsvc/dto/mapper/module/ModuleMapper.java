package com.github.ngodat0103.yamp.authsvc.dto.mapper.module;

import com.github.ngodat0103.yamp.authsvc.dto.module.ModuleDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.module.Modules;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ModuleMapper {
  ModuleMapper INSTANCE = Mappers.getMapper(ModuleMapper.class);

  ModuleDto toDto(Modules modules);

  Modules toEntity(ModuleDto moduleDto);
}
