package com.github.ngodat0103.yamp.authsvc.service.impl;


import com.github.ngodat0103.yamp.authsvc.dto.RoleDto;
import com.github.ngodat0103.yamp.authsvc.dto.mapper.RoleMapper;
import com.github.ngodat0103.yamp.authsvc.exception.ConflictException;
import com.github.ngodat0103.yamp.authsvc.exception.NotFoundException;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Role;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.RoleRepository;
import com.github.ngodat0103.yamp.authsvc.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
public class RoleServiceImpl  implements RoleService {
    private RoleRepository roleRepository;
    private RoleMapper roleMapper;


    @Override
    public Set<RoleDto> getRole() {
        log.debug("Fetching all roles");
        return roleRepository.findAll().stream().map(roleMapper::mapToDto).collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public void addRole(RoleDto roleDto) {

        Role role = roleMapper.mapToEntity(roleDto);
        log.debug("Fetching role: {}", roleDto.getRoleName());
        if(roleRepository.existsByRoleName(role.getRoleName())){
            log.debug("Role name conflict: {}, Rollback", roleDto.getRoleName());
            throw new ConflictException("Role name conflict");
        }
        roleRepository.save(roleMapper.mapToEntity(roleDto));
        log.debug("Role added: {}", roleDto.getRoleName());
    }

    @Override
    public void updateRole(RoleDto roleDto) {
        Role role = getRole(roleDto.getRoleName());
        log.debug("Updating role: {}", roleDto.getRoleName());
        role.setRoleName(roleDto.getRoleName());
        role.setRoleDescription(roleDto.getRoleDescription());
        roleRepository.save(role);
        log.debug("Role updated: {}", roleDto.getRoleName());
    }

    @Override
    public void deleteRole(String roleName) {
        Role role = getRole(roleName);
        roleRepository.delete(role);
    }

   private Role getRole(String roleName){
        return roleRepository.findRoleByRoleName(roleName.toUpperCase()).orElseThrow(()-> {
            log.debug("Role name not found: {}, Rollback", roleName);
            return new NotFoundException("Role name not found");
        });
   }
}
