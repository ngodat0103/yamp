package com.github.ngodat0103.yamp.authsvc.service.impl;

import static com.github.ngodat0103.yamp.authsvc.Util.*;

import com.github.ngodat0103.yamp.authsvc.dto.ModuleDto;
import com.github.ngodat0103.yamp.authsvc.dto.mapper.ModuleMapper;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Module;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.ModuleRepository;
import com.github.ngodat0103.yamp.authsvc.service.EntityCrudService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ModuleServiceImpl implements EntityCrudService<Module, ModuleDto, Long> {

  private ModuleRepository moduleRepository;
  private ModuleMapper moduleMapper;

  @Override
  public ModuleDto create(ModuleDto newDto) {
    Module newModule = moduleMapper.toEntity(newDto);
    if (moduleRepository.existsByName(newModule.getName())) {
      throwConflictException(log, "Module", "name", newModule.getName());
    }
    Module savedModule = moduleRepository.save(newModule);
    return moduleMapper.toDto(savedModule);
  }

  @Override
  public ModuleDto readById(Long id) {
    Module module =
        moduleRepository
            .findById(id)
            .orElseThrow(notFoundExceptionSupplier(log, "Module", "id", id));
    return moduleMapper.toDto(module);
  }

  @Override
  public ModuleDto update(Long id, ModuleDto updateDto) {
    Module oldModule =
        moduleRepository
            .findById(id)
            .orElseThrow(notFoundExceptionSupplier(log, "Module", "id", id));

    oldModule.setName(updateDto.getName());
    Module updatedModule = moduleRepository.save(oldModule);
    return moduleMapper.toDto(updatedModule);
  }

  @Override
  public void deleteById(Long integer) {
    if (!moduleRepository.existsById(integer)) {
      throwNotFoundException(log, "Module", "id", integer);
    }
    moduleRepository.deleteById(integer);
  }

  @Override
  public List<ModuleDto> findAll() {
    return moduleRepository.findAll().stream().map(moduleMapper::toDto).toList();
  }
}
