package com.github.ngodat0103.yamp.authsvc.service.impl;

import com.github.ngodat0103.yamp.authsvc.dto.role.RolesDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.roles.Roles;
import com.github.ngodat0103.yamp.authsvc.service.EntityCrudService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class RoleServiceImpl implements EntityCrudService<Roles, RolesDto, Integer> {

  @Override
  public RolesDto create(RolesDto newDto) {
    return null;
  }

  @Override
  public RolesDto readById(Integer integer) {
    return null;
  }

  @Override
  public RolesDto update(Integer integer, RolesDto updateDto) {
    return null;
  }

  @Override
  public void deleteById(Integer integer) {}

  @Override
  public List<RolesDto> findAll() {
    return List.of();
  }
}
