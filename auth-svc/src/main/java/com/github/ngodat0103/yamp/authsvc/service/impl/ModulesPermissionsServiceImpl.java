package com.github.ngodat0103.yamp.authsvc.service.impl;

import static com.github.ngodat0103.yamp.authsvc.Util.*;

import com.github.ngodat0103.yamp.authsvc.dto.mapper.permission.ModulePermissionMapper;
import com.github.ngodat0103.yamp.authsvc.dto.permission.ModulePermissionDetailDto;
import com.github.ngodat0103.yamp.authsvc.dto.permission.ModulePermissionDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.ModulePermission;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.ModulePermissionRepository;
import com.github.ngodat0103.yamp.authsvc.service.EntityCrudService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ModulesPermissionsServiceImpl
    implements EntityCrudService<ModulePermission, ModulePermissionDto, Long> {
  private ModulePermissionRepository modulePermissionRepository;
  private ModulePermissionMapper modulePermissionMapper;

  @Override
  public ModulePermissionDto create(ModulePermissionDto newDto) {

    ModulePermission modulePermission = modulePermissionMapper.toEntity(newDto);
    checkNull(modulePermission, newDto);
    modulePermission = modulePermissionRepository.save(modulePermission);
    return modulePermissionMapper.toDto(modulePermission);
  }

  @Override
  public ModulePermissionDto readById(Long id) {
    var modulePermission =
        modulePermissionRepository
            .findById(id)
            .orElseThrow(notFoundExceptionSupplier(log, "ModulePermission", "id", id));
    return modulePermissionMapper.toDto(modulePermission);
  }
  public ModulePermissionDetailDto readDetailById(Long id){
    var modulePermission =
            modulePermissionRepository
                    .findById(id)
                    .orElseThrow(notFoundExceptionSupplier(log, "ModulePermission", "id", id));
    return modulePermissionMapper.toDetailDto(modulePermission);
  }
  public List<ModulePermissionDetailDto> findAllDetail(){
    return modulePermissionRepository.findAll().stream()
            .map(modulePermissionMapper::toDetailDto)
            .toList();
  }

  @Override
  public ModulePermissionDto update(Long id, ModulePermissionDto updateDto) {
    if (!modulePermissionRepository.existsById(id)) {
      throwNotFoundException(log, "ModulePermission", "id", id);
    }
    var modulePermission = modulePermissionMapper.toEntity(updateDto);
    checkNull(modulePermission, updateDto);
    modulePermission = modulePermissionRepository.save(modulePermission);
    return modulePermissionMapper.toDto(modulePermission);
  }

  @Override
  public void deleteById(Long id) {
    if (!modulePermissionRepository.existsById(id)) {
      throwNotFoundException(log, "ModulePermission", "id", id);
    }
    modulePermissionRepository.deleteById(id);
  }

  @Override
  public List<ModulePermissionDto> findAll() {
    return modulePermissionRepository.findAll().stream()
        .map(modulePermissionMapper::toDto)
        .toList();
  }

  private void checkNull(
      ModulePermission modulePermission, ModulePermissionDto modulePermissionDto) {
    if (modulePermission.getModule() == null) {
      throwNotFoundException(log, "Module", "id", modulePermissionDto.getModulesId());
    }
    if (modulePermission.getPermission() == null) {
      throwNotFoundException(log, "Permission", "id", modulePermissionDto.getPermissionsId());
    }
  }
}
