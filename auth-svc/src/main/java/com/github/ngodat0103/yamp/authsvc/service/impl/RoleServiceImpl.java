package com.github.ngodat0103.yamp.authsvc.service.impl;

import static com.github.ngodat0103.yamp.authsvc.Util.*;

import com.github.ngodat0103.yamp.authsvc.dto.mapper.role.RoleMapper;
import com.github.ngodat0103.yamp.authsvc.dto.role.RoleDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.role.Role;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.RoleRepository;
import com.github.ngodat0103.yamp.authsvc.service.EntityCrudService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class RoleServiceImpl implements EntityCrudService<Role, RoleDto, Long> {
  private RoleRepository roleRepository;
  private RoleMapper roleMapper;

  @Override
  public RoleDto create(RoleDto newDto) {
    String roleName = newDto.getName();
    if (roleRepository.existsByName(roleName)) {
      throwConflictException(log, "Role", "name", roleName);
    }
    Role role = roleMapper.toEntity(newDto);
    return roleMapper.toDto(roleRepository.save(role));
  }

  @Override
  public RoleDto readById(Long id) {
    var role =
        roleRepository.findById(id).orElseThrow(notFoundExceptionSupplier(log, "Role", "id", id));
    return roleMapper.toDto(role);
  }

  @Override
  public RoleDto update(Long id, RoleDto updateDto) {
    var role =
        roleRepository.findById(id).orElseThrow(notFoundExceptionSupplier(log, "Role", "id", id));
    if (roleRepository.existsByName(updateDto.getName())) {
      throwConflictException(log, "Role", "id", id);
    }
    role.setName(updateDto.getName());
    return roleMapper.toDto(role);
  }

  @Override
  public void deleteById(Long id) {
    if (!roleRepository.existsById(id)) {
      throwNotFoundException(log, "Role", "id", id);
    }
    roleRepository.deleteById(id);
  }

  @Override
  public List<RoleDto> findAll() {
    return roleRepository.findAll().stream().map(roleMapper::toDto).toList();
  }
}
