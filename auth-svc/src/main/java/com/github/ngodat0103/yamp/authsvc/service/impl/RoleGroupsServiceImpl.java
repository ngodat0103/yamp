package com.github.ngodat0103.yamp.authsvc.service.impl;

import com.github.ngodat0103.yamp.authsvc.dto.role.RoleGroupsDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.role.RoleGroups;
import com.github.ngodat0103.yamp.authsvc.service.EntityCrudService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleGroupsServiceImpl
    implements EntityCrudService<RoleGroups, RoleGroupsDto, Integer> {

  @Override
  public RoleGroupsDto create(RoleGroupsDto newDto) {
    return null;
  }

  @Override
  public RoleGroupsDto readById(Integer integer) {
    return null;
  }

  @Override
  public RoleGroupsDto update(Integer integer, RoleGroupsDto updateDto) {
    return null;
  }

  @Override
  public void deleteById(Integer integer) {}

  @Override
  public List<RoleGroupsDto> findAll() {
    return List.of();
  }
}
