package com.github.ngodat0103.yamp.authsvc.dto.mapper.permission.impl;

import com.github.ngodat0103.yamp.authsvc.dto.mapper.permission.PermissionMapper;
import com.github.ngodat0103.yamp.authsvc.dto.mapper.permission.RolePermissionMapper;
import com.github.ngodat0103.yamp.authsvc.dto.mapper.role.RoleMapper;
import com.github.ngodat0103.yamp.authsvc.dto.permission.PermissionDto;
import com.github.ngodat0103.yamp.authsvc.dto.permission.RolePermissionDetailDto;
import com.github.ngodat0103.yamp.authsvc.dto.permission.RolePermissionDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.RolePermission;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.RolePermissionId;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.PermissionRepository;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RolePermissionMapperImpl implements RolePermissionMapper {
  private RoleRepository roleRepository;
  private PermissionRepository permissionRepository;
  private RoleMapper roleMapper;
  private PermissionMapper permissionMapper;

  @Override
  public RolePermissionDto toDto(RolePermission rolePermission) {
    return RolePermissionDto.builder()
        .permissionId(rolePermission.getPermission().getId())
        .roleId(rolePermission.getRole().getId())
        .build();
  }

  @Override
  public RolePermissionDetailDto toDetailDto(RolePermission rolePermission) {
    var role = rolePermission.getRole();
    var permission = rolePermission.getPermission();
    return RolePermissionDetailDto.builder()
        .permissionDto(permissionMapper.toDto(permission))
        .roleDto(roleMapper.toDto(role))
        .build();
  }

  @Override
  public RolePermission toEntity(RolePermissionDto rolePermissionDto) {
    var role = roleRepository.findById(rolePermissionDto.getRoleId()).orElse(null);
    var permission =
        permissionRepository.findById(rolePermissionDto.getPermissionId()).orElse(null);
    RolePermission rolePermission = new RolePermission();
    rolePermission.setPermission(permission);
    rolePermission.setRole(role);
    RolePermissionId rolePermissionId = new RolePermissionId();
    rolePermissionId.setPermissionId(permission != null ? permission.getId() : null);
    rolePermissionId.setRoleId(role != null ? role.getId() : null);
    rolePermission.setRolePermissionId(rolePermissionId);
    return rolePermission;
  }

  @Override
  public PermissionDto toPermissionDto(RolePermission rolePermission) {
    var permission = rolePermission.getPermission();
    return PermissionDto.builder().id(permission.getId()).name(permission.getName()).build();
  }
}
