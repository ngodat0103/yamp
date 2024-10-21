package com.github.ngodat0103.yamp.authsvc.service.impl;

import com.github.ngodat0103.yamp.authsvc.dto.permission.PermissionsDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.Permissions;
import com.github.ngodat0103.yamp.authsvc.service.EntityCrudService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PermissionsServiceImpl
    implements EntityCrudService<Permissions, PermissionsDto, Integer> {

  @Override
  public PermissionsDto create(PermissionsDto newDto) {
    return null;
  }

  @Override
  public PermissionsDto readById(Integer integer) {
    return null;
  }

  @Override
  public PermissionsDto update(Integer integer, PermissionsDto updateDto) {
    return null;
  }

  @Override
  public void deleteById(Integer integer) {

  }

  @Override
  public List<PermissionsDto> findAll() {
    return List.of();
  }
}
