package com.github.ngodat0103.yamp.authsvc.service.impl;

import static com.github.ngodat0103.yamp.authsvc.Util.*;

import com.github.ngodat0103.yamp.authsvc.dto.mapper.module.ModuleMapper;
import com.github.ngodat0103.yamp.authsvc.dto.module.ModuleDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.module.Modules;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.ModulesRepository;
import com.github.ngodat0103.yamp.authsvc.service.EntityCrudService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ModulesServiceImpl implements EntityCrudService<Modules, ModuleDto, Long> {

  private ModulesRepository modulesRepository;
  private ModuleMapper moduleMapper;

  @Override
  public ModuleDto create(ModuleDto newDto) {
    Modules newModule = moduleMapper.toEntity(newDto);
    if (modulesRepository.existsByName(newModule.getName())) {
      throwConflictException(log, "Module", "name", newModule.getName());
    }
    Modules savedModule = modulesRepository.save(newModule);
    return moduleMapper.toDto(savedModule);
  }

  @Override
  public ModuleDto readById(Long id) {
   Modules modules =  modulesRepository.findById(id)
            .orElseThrow(notFoundExceptionSupplier(log, "Module", "id", id));
   return moduleMapper.toDto(modules);
  }

  @Override
  public ModuleDto update(Long id, ModuleDto updateDto) {
    Modules oldModule =
        modulesRepository
            .findById(id)
            .orElseThrow(notFoundExceptionSupplier(log, "Module", "id", id));

    oldModule.setName(updateDto.getName());
    Modules updatedModule = modulesRepository.save(oldModule);
    return moduleMapper.toDto(updatedModule);
  }

  @Override
  public void deleteById(Long integer) {
    if (!modulesRepository.existsById(integer)) {
      throwNotFoundException(log, "Module", "id", integer);
    }
    modulesRepository.deleteById(integer);
  }

  @Override
  public List<ModuleDto> findAll() {
    return modulesRepository.findAll().stream().map(moduleMapper::toDto).toList();
  }
}
