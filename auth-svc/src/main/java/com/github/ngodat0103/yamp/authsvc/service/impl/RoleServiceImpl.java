package com.github.ngodat0103.yamp.authsvc.service.impl;

import static com.github.ngodat0103.yamp.authsvc.Util.*;

import com.github.ngodat0103.yamp.authsvc.dto.mapper.PermissionMapper;
import com.github.ngodat0103.yamp.authsvc.dto.mapper.RoleMapper;
import com.github.ngodat0103.yamp.authsvc.dto.permission.RolePermissionsDto;
import com.github.ngodat0103.yamp.authsvc.dto.role.RoleDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.role.Role;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.PermissionRepository;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.RoleRepository;
import com.github.ngodat0103.yamp.authsvc.service.AbstractCrudService;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleServiceImpl extends AbstractCrudService<RoleDto, Role, Long> {
  private final RoleRepository roleRepository;
  private final PermissionMapper permissionMapper;
  private final PermissionRepository permissionRepository;

  public RoleServiceImpl(
      RoleMapper roleMapper,
      RoleRepository roleRepository,
      PermissionMapper permissionMapper,
      PermissionRepository permissionRepository) {
    super(roleMapper);
    this.roleRepository = roleRepository;
    this.permissionMapper = permissionMapper;
    this.permissionRepository = permissionRepository;
  }

  @Override
  public RoleDto create(RoleDto newDto) {
    Role role = this.getMapper().toEntity(newDto);
    if (roleRepository.existsByName(role.getName())) {
      throwConflictException(log, "Role", "id", role.getId());
    }
    return super.getMapper().toDto(roleRepository.save(role));
  }

  @Override
  public RoleDto readById(Long id) {
    var role =
        roleRepository.findById(id).orElseThrow(notFoundExceptionSupplier(log, "Role", "id", id));
    return super.getMapper().toDto(role);
  }

  public RoleDto readByName(String name) {
    var role =
        roleRepository
            .findByName(name)
            .orElseThrow(notFoundExceptionSupplier(log, "Role", "name", name));
    return super.getMapper().toDto(role);
  }

  public RolePermissionsDto getPermissions(Long id) {
    var role =
        roleRepository.findById(id).orElseThrow(notFoundExceptionSupplier(log, "Role", "id", id));

    var permissions =
        role.getPermissions().stream()
            .map(permissionMapper::toDto)
            .collect(Collectors.toUnmodifiableSet());
    return RolePermissionsDto.builder()
        .id(role.getId())
        .name(role.getName())
        .permissions(permissions)
        .build();
  }

  @Override
  public RoleDto update(Long aLong, RoleDto updateDto) {
    if (!roleRepository.existsById(aLong)) {
      throwNotFoundException(log, "Role", "id", aLong);
    }
    var role = this.getMapper().toEntity(updateDto);
    if (role.getName() != null && roleRepository.existsByName(role.getName())) {
      throwConflictException(log, "Role", "name", role.getName());
    }
    return super.getMapper().toDto(roleRepository.save(role));
  }

  @Override
  public void deleteById(Long aLong) {
    if (!roleRepository.existsById(aLong)) {
      throwNotFoundException(log, "Role", "id", aLong);
    }
    roleRepository.deleteById(aLong);
  }

  @Override
  public Set<RoleDto> findAll() {
    return roleRepository.findAll().stream()
        .map(super.getMapper()::toDto)
        .collect(Collectors.toUnmodifiableSet());
  }

  public RolePermissionsDto addPermissions(Long roleId, Set<Long> permissionIds) {
    var role =
        roleRepository
            .findById(roleId)
            .orElseThrow(notFoundExceptionSupplier(log, "Role", "id", roleId));

    var permissions =
        permissionRepository.findAllById(permissionIds).stream()
            .collect(Collectors.toUnmodifiableSet());
    role.setPermissions(permissions);
    role = roleRepository.save(role);
    return RolePermissionsDto.builder()
        .id(role.getId())
        .name(role.getName())
        .permissions(
            permissions.stream()
                .map(permissionMapper::toDto)
                .collect(Collectors.toUnmodifiableSet()))
        .build();
  }

  public RolePermissionsDto deletePermissions(Long roleId, Set<Long> permissionIds) {
    var role =
        roleRepository
            .findById(roleId)
            .orElseThrow(notFoundExceptionSupplier(log, "Role", "id", roleId));

    var permissions =
        permissionRepository.findAllById(permissionIds).stream()
            .collect(Collectors.toUnmodifiableSet());
    role.getPermissions().removeAll(permissions);
    role = roleRepository.save(role);
    return RolePermissionsDto.builder()
        .id(role.getId())
        .name(role.getName())
        .permissions(
            role.getPermissions().stream()
                .map(permissionMapper::toDto)
                .collect(Collectors.toUnmodifiableSet()))
        .build();
  }
}
