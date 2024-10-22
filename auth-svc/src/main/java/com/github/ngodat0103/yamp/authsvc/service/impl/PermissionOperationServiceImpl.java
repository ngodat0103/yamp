package com.github.ngodat0103.yamp.authsvc.service.impl;

import static com.github.ngodat0103.yamp.authsvc.Util.*;

import com.github.ngodat0103.yamp.authsvc.dto.mapper.permission.PermissionOperationMapper;
import com.github.ngodat0103.yamp.authsvc.dto.permission.PermissionOperationDetailDto;
import com.github.ngodat0103.yamp.authsvc.dto.permission.PermissionOperationDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.PermissionOperation;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.PermissionOperationRepository;
import com.github.ngodat0103.yamp.authsvc.service.EntityCrudService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class PermissionOperationServiceImpl
    implements EntityCrudService<PermissionOperation, PermissionOperationDto, Long> {
  private PermissionOperationRepository permissionOperationRepository;
  private PermissionOperationMapper permissionOperationMapper;

  @Override
  public PermissionOperationDto create(PermissionOperationDto newDto) {
    PermissionOperation permissionOperation = permissionOperationMapper.toEntity(newDto);
    checkNull(permissionOperation, newDto);
    permissionOperation = permissionOperationRepository.save(permissionOperation);
    return permissionOperationMapper.toDto(permissionOperation);
  }

  @Override
  public PermissionOperationDto readById(Long id) {
    var permissionOperation =
        permissionOperationRepository
            .findById(id)
            .orElseThrow(notFoundExceptionSupplier(log, "PermissionOperation", "id", id));
    return permissionOperationMapper.toDto(permissionOperation);
  }

  public PermissionOperationDetailDto readDetailById(Long id) {
    var permissionOperation =
        permissionOperationRepository
            .findById(id)
            .orElseThrow(notFoundExceptionSupplier(log, "PermissionOperation", "id", id));
    return permissionOperationMapper.toDetailDto(permissionOperation);
  }

  public List<PermissionOperationDetailDto> findAllDetail() {
    return permissionOperationRepository.findAll().stream()
        .map(permissionOperationMapper::toDetailDto)
        .toList();
  }

  @Override
  public PermissionOperationDto update(Long id, PermissionOperationDto updateDto) {
    if (!permissionOperationRepository.existsById(id)) {
      throwNotFoundException(log, "PermissionOperation", "id", id);
    }
    var permissionOperation = permissionOperationMapper.toEntity(updateDto);
    checkNull(permissionOperation, updateDto);
    permissionOperation = permissionOperationRepository.save(permissionOperation);
    return permissionOperationMapper.toDto(permissionOperation);
  }

  @Override
  public void deleteById(Long id) {
    if (!permissionOperationRepository.existsById(id)) {
      throwNotFoundException(log, "PermissionOperation", "id", id);
    }
    permissionOperationRepository.deleteById(id);
  }

  @Override
  public List<PermissionOperationDto> findAll() {
    return permissionOperationRepository.findAll().stream()
        .map(permissionOperationMapper::toDto)
        .toList();
  }

  private void checkNull(
      PermissionOperation permissionOperation, PermissionOperationDto permissionOperationDto) {
    if (permissionOperation.getOperation() == null) {
      throwNotFoundException(log, "Operation", "id", permissionOperationDto.getOperationId());
    }
    if (permissionOperation.getPermission() == null) {
      throwNotFoundException(log, "Permission", "id", permissionOperationDto.getPermissionId());
    }
  }
}
