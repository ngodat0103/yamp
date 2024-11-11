package com.github.ngodat0103.yamp.authsvc.service.impl;

import static com.github.ngodat0103.yamp.authsvc.Util.*;

import com.github.ngodat0103.yamp.authsvc.dto.ModuleDto;
import com.github.ngodat0103.yamp.authsvc.dto.mapper.ModuleMapper;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Module;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.ModuleRepository;
import com.github.ngodat0103.yamp.authsvc.service.AbstractCrudService;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ModuleServiceImpl extends AbstractCrudService<ModuleDto, Module, Long> {
  private final ModuleRepository moduleRepository;

  public ModuleServiceImpl(ModuleRepository moduleRepository, ModuleMapper moduleMapper) {
    super(moduleMapper);
    this.moduleRepository = moduleRepository;
  }

  @Override
  public ModuleDto create(ModuleDto newDto) {
    Module module = super.getMapper().toEntity(newDto);
    if (moduleRepository.existsByName(module.getName())) {
      throwConflictException(log, "module", "name", newDto.getName());
    }
    return super.getMapper().toDto(moduleRepository.save(module));
  }

  @Override
  public ModuleDto readById(Long id) {
    return moduleRepository
        .findById(id)
        .map(super.mapper::toDto)
        .orElseThrow(notFoundExceptionSupplier(log, "module", "id", id));
  }

  @Override
  public ModuleDto update(Long aLong, ModuleDto updateDto) {
    var module =
        moduleRepository
            .findById(aLong)
            .orElseThrow(notFoundExceptionSupplier(log, "module", "id", aLong));
    if (moduleRepository.existsByName(updateDto.getName())) {
      throwConflictException(log, "module", "name", updateDto.getName());
    }
    module.setName(updateDto.getName());
    return super.getMapper().toDto(moduleRepository.save(module));
  }

  @Override
  public void deleteById(Long id) {
    if (!moduleRepository.existsById(id)) {
      throw notFoundExceptionSupplier(log, "module", "id", id).get();
    }
    moduleRepository.deleteById(id);
  }

  @Override
  public Set<ModuleDto> findAll() {
    return moduleRepository.findAll().stream()
        .map(super.mapper::toDto)
        .collect(Collectors.toUnmodifiableSet());
  }
}
