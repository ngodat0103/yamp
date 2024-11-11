package com.github.ngodat0103.yamp.authsvc.service.impl;

import static com.github.ngodat0103.yamp.authsvc.Util.*;

import com.github.ngodat0103.yamp.authsvc.dto.mapper.OperationMapper;
import com.github.ngodat0103.yamp.authsvc.dto.mapper.PermissionMapper;
import com.github.ngodat0103.yamp.authsvc.dto.permission.PermissionDto;
import com.github.ngodat0103.yamp.authsvc.dto.permission.PermissionOperationsDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Permission;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.OperationRepository;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.PermissionRepository;
import com.github.ngodat0103.yamp.authsvc.service.AbstractCrudService;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PermissionServiceImpl extends AbstractCrudService<PermissionDto, Permission, Long> {
  private final PermissionRepository permissionRepository;
  private final OperationMapper operationMapper;
  private final OperationRepository operationRepository;
  public PermissionServiceImpl(
          PermissionMapper permissionMapper,
          PermissionRepository permissionRepository,
          OperationMapper operationMapper,
          OperationRepository operationRepository) {
    super(permissionMapper);
    this.permissionRepository = permissionRepository;
    this.operationMapper = operationMapper;
    this.operationRepository = operationRepository;
  }

  @Override
  public PermissionDto create(PermissionDto newDto) {
    var permission = super.getMapper().toEntity(newDto);
    if (permissionRepository.existsByName(permission.getName())) {
      throwConflictException(log, "Permission", "id", permission.getId());
    }
    return super.getMapper().toDto(permissionRepository.save(permission));
  }

  @Override
  public PermissionDto readById(Long id) {
    return permissionRepository
        .findById(id)
        .map(super.getMapper()::toDto)
        .orElseThrow(notFoundExceptionSupplier(log, "Permission", "id", id));
  }

  @Override
  public PermissionDto update(Long id, PermissionDto updateDto) {
    var permission =
        permissionRepository
            .findById(id)
            .orElseThrow(notFoundExceptionSupplier(log, "Permission", "id", id));

    if (permissionRepository.existsByName(updateDto.getName())) {
      throwConflictException(log, "Permission", "id", id);
    }
    permission.setName(updateDto.getName());
    permission = permissionRepository.save(permission);
    return super.getMapper().toDto(permission);
  }

  @Override
  public void deleteById(Long id) {
    if (!permissionRepository.existsById(id)) {
      throwNotFoundException(log, "Permission", "id", id);
    }
    permissionRepository.deleteById(id);
  }

  @Override
  public Set<PermissionDto> findAll() {
    return permissionRepository.findAll().stream()
        .map(super.getMapper()::toDto)
        .collect(Collectors.toUnmodifiableSet());
  }

  public PermissionOperationsDto getOperations(Long id) {
    var permission =
        permissionRepository
            .findById(id)
            .orElseThrow(notFoundExceptionSupplier(log, "Permission", "id", id));

    var operations =
        permission.getOperations().stream()
            .map(this.operationMapper::toDto)
            .collect(Collectors.toUnmodifiableSet());
    return PermissionOperationsDto.builder()
        .id(permission.getId())
        .name(permission.getName())
        .operationDtos(operations)
        .build();
  }

  public PermissionOperationsDto addOperations(Long permissionId, Set<Long> operationIds) {
    var permission =
        permissionRepository
            .findById(permissionId)
            .orElseThrow(notFoundExceptionSupplier(log, "Permission", "id", permissionId));

    var operations =
        operationRepository.findAllById(operationIds).stream()
            .collect(Collectors.toUnmodifiableSet());
    permission.setOperations(operations);
    permission = permissionRepository.save(permission);
    return PermissionOperationsDto.builder()
        .id(permission.getId())
        .name(permission.getName())
        .operationDtos(
            operations.stream()
                .map(this.operationMapper::toDto)
                .collect(Collectors.toUnmodifiableSet()))
        .build();
  }

  public PermissionOperationsDto deleteOperations(Long permissionId, Set<Long> operationIds) {
    var permission =
        permissionRepository
            .findById(permissionId)
            .orElseThrow(notFoundExceptionSupplier(log, "Permission", "id", permissionId));

    var operations =
        operationRepository.findAllById(operationIds).stream()
            .collect(Collectors.toUnmodifiableSet());
    permission.getOperations().removeAll(operations);
    permission = permissionRepository.save(permission);
    return PermissionOperationsDto.builder()
        .id(permission.getId())
        .name(permission.getName())
        .operationDtos(
            permission.getOperations().stream()
                .map(this.operationMapper::toDto)
                .collect(Collectors.toUnmodifiableSet()))
        .build();
  }
}
