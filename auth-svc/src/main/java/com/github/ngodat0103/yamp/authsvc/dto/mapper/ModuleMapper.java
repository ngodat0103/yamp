package com.github.ngodat0103.yamp.authsvc.dto.mapper;

import com.github.ngodat0103.yamp.authsvc.dto.ModuleDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Module;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModuleMapper extends AbstractMapper<ModuleDto, Module> {}
