package com.github.ngodat0103.yamp.authsvc.service.impl;

import static com.github.ngodat0103.yamp.authsvc.Util.*;

import com.github.ngodat0103.yamp.authsvc.dto.mapper.permission.RolePermissionMapper;
import com.github.ngodat0103.yamp.authsvc.dto.permission.PermissionDto;
import com.github.ngodat0103.yamp.authsvc.dto.permission.RolePermissionDetailDto;
import com.github.ngodat0103.yamp.authsvc.dto.permission.RolePermissionDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.RolePermission;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.RolePermissionId;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.RolePermissionRepository;
import com.github.ngodat0103.yamp.authsvc.service.EntityCrudService;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class RolePermissionImpl
    implements EntityCrudService<RolePermission, RolePermissionDto, RolePermissionId> {

  private RolePermissionRepository rolePermissionRepository;
  private RolePermissionMapper rolePermissionMapper;

  @Override
  public RolePermissionDto create(RolePermissionDto newDto) {
    var rolePermission = rolePermissionMapper.toEntity(newDto);

    if (rolePermission.getRole() == null) {
      throwNotFoundException(log, "Role", "id", newDto.getRoleId());
    }
    if (rolePermission.getPermission() == null) {
      throwNotFoundException(log, "role", "id", newDto.getPermissionId());
    }

    if (rolePermissionRepository.existsById(rolePermission.getRolePermissionId())) {
      throwConflictException(
          log, "RolePermission", "Primary key", rolePermission.getRolePermissionId());
    }
    rolePermission = rolePermissionRepository.save(rolePermission);
    return rolePermissionMapper.toDto(rolePermission);
  }

  @Override
  public RolePermissionDto readById(RolePermissionId rolePermissionId) {
    throw new UnsupportedOperationException();
  }

  public Set<PermissionDto> readPermissionByRoleId(Long roleId) {
    return rolePermissionRepository.findByRoleId(roleId).stream()
        .map(rolePermissionMapper::toPermissionDto)
        .collect(Collectors.toUnmodifiableSet());
  }

  @Override
  public RolePermissionDto update(RolePermissionId id, RolePermissionDto updateDto) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void deleteById(RolePermissionId rolePermissionId) {}

  public List<RolePermissionDetailDto> findDetailAll() {
    return rolePermissionRepository.findAll().stream()
        .map(rolePermissionMapper::toDetailDto)
        .toList();
  }

  @Override
  public List<RolePermissionDto> findAll() {
    throw new UnsupportedOperationException();
  }
}
