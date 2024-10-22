package com.github.ngodat0103.yamp.authsvc.dto.mapper.permission.impl;

import com.github.ngodat0103.yamp.authsvc.dto.mapper.OperationMapper;
import com.github.ngodat0103.yamp.authsvc.dto.mapper.permission.PermissionMapper;
import com.github.ngodat0103.yamp.authsvc.dto.mapper.permission.PermissionOperationMapper;
import com.github.ngodat0103.yamp.authsvc.dto.permission.PermissionOperationDetailDto;
import com.github.ngodat0103.yamp.authsvc.dto.permission.PermissionOperationDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Operation;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.Permission;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.PermissionOperation;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.OperationRepository;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.PermissionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class PermissionOperationMapperImpl implements PermissionOperationMapper {
  private OperationRepository operationRepository;
  private PermissionRepository permissionRepository;
  private OperationMapper operationMapper;
  private PermissionMapper permissionMapper;

  @Override
  public PermissionOperationDto toDto(PermissionOperation permissionOperation) {
    return PermissionOperationDto.builder()
        .id(permissionOperation.getId())
        .operationId(permissionOperation.getOperation().getId())
        .permissionId(permissionOperation.getPermission().getId())
        .build();
  }

  @Override
  public PermissionOperationDetailDto toDetailDto(PermissionOperation permissionOperation) {
    return PermissionOperationDetailDto.builder()
        .id(permissionOperation.getId())
        .operationDto(operationMapper.toDto(permissionOperation.getOperation()))
        .permissionDto(permissionMapper.toDto(permissionOperation.getPermission()))
        .build();
  }

  @Override
  public PermissionOperation toEntity(PermissionOperationDto permissionOperationDto) {
    PermissionOperation permissionOperation = new PermissionOperation();
    Long operationId = permissionOperationDto.getOperationId();
    Long permissionId = permissionOperationDto.getPermissionId();
    Operation operation = operationRepository.findById(operationId).orElse(null);
    Permission permission = permissionRepository.findById(permissionId).orElse(null);
    permissionOperation.setOperation(operation);
    permissionOperation.setPermission(permission);
    return permissionOperation;
  }
}
