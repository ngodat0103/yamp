package com.github.ngodat0103.yamp.authsvc.service.impl;

import static com.github.ngodat0103.yamp.authsvc.Util.*;

import com.github.ngodat0103.yamp.authsvc.dto.mapper.permission.PermissionMapper;
import com.github.ngodat0103.yamp.authsvc.dto.permission.PermissionDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.Permission;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.PermissionRepository;
import com.github.ngodat0103.yamp.authsvc.service.EntityCrudService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class PermissionServiceImpl implements EntityCrudService<Permission, PermissionDto, Long> {
  private PermissionRepository permissionRepository;
  private PermissionMapper permissionMapper;

  @Override
  public PermissionDto create(PermissionDto newDto) {
    if (permissionRepository.existsByName(newDto.getName())) {
      throwConflictException(log, "Permission", "name", newDto.getName());
    }
    Permission newEntity = permissionMapper.toEntity(newDto);
    return permissionMapper.toDto(permissionRepository.save(newEntity));
  }

  @Override
  public PermissionDto readById(Long id) {
    var permission =
        permissionRepository
            .findById(id)
            .orElseThrow(notFoundExceptionSupplier(log, "Permission", "id", id));
    return permissionMapper.toDto(permission);
  }

  @Override
  public PermissionDto update(Long id, PermissionDto updateDto) {
    var permission =
        permissionRepository
            .findById(id)
            .orElseThrow(notFoundExceptionSupplier(log, "Permission", "id", id));
    if (permissionRepository.existsByName(updateDto.getName())) {
      throwConflictException(log, "Permission", "name", updateDto.getName());
    }
    permission.setName(updateDto.getName());
    return permissionMapper.toDto(permissionRepository.save(permission));
  }

  @Override
  public void deleteById(Long id) {
    if (!permissionRepository.existsById(id)) {
      throwNotFoundException(log, "Permission", "id", id);
    }
    permissionRepository.deleteById(id);
  }

  @Override
  public List<PermissionDto> findAll() {
    return permissionRepository.findAll().stream().map(permissionMapper::toDto).toList();
  }
}
