package com.github.ngodat0103.yamp.authsvc.dto.mapper.permission.impl;

import com.github.ngodat0103.yamp.authsvc.dto.mapper.ModuleMapper;
import com.github.ngodat0103.yamp.authsvc.dto.mapper.permission.ModulePermissionMapper;
import com.github.ngodat0103.yamp.authsvc.dto.mapper.permission.PermissionMapper;
import com.github.ngodat0103.yamp.authsvc.dto.permission.ModulePermissionDetailDto;
import com.github.ngodat0103.yamp.authsvc.dto.permission.ModulePermissionDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Module;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.ModulePermission;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.Permission;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.ModuleRepository;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.PermissionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class ModulePermissionMapperImpl implements ModulePermissionMapper {
  private ModuleRepository moduleRepository;
  private PermissionRepository permissionRepository;
  private ModuleMapper moduleMapper;
  private PermissionMapper permissionMapper;

  @Override
  public ModulePermissionDto toDto(ModulePermission modulePermission) {
    return ModulePermissionDto.builder()
        .id(modulePermission.getId())
        .modulesId(modulePermission.getModule().getId())
        .permissionsId(modulePermission.getPermission().getId())
        .build();
  }

  @Override
  public ModulePermissionDetailDto toDetailDto(ModulePermission modulePermission) {
    return ModulePermissionDetailDto.builder()
            .id(modulePermission.getId())
            .moduleDto(moduleMapper.toDto(modulePermission.getModule()))
            .permissionDto(permissionMapper.toDto(modulePermission.getPermission()))
            .build();
  }

  @Override
  public ModulePermission toEntity(ModulePermissionDto modulePermissionDto) {
    ModulePermission modulePermission = new ModulePermission();
    Long moduleId = modulePermissionDto.getModulesId();
    Long permissionId = modulePermissionDto.getPermissionsId();
    Module module = moduleRepository.findById(moduleId).orElse(null);
    Permission permission = permissionRepository.findById(permissionId).orElse(null);
    modulePermission.setPermission(permission);
    modulePermission.setModule(module);
    return modulePermission;
  }
}
