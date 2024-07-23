package org.example.authservice.service.impl;

import org.example.authservice.entity.Role;
import org.example.authservice.exception.ApiException;
import org.example.authservice.repository.RoleRepository;
import org.example.authservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role getRole(String roleName) {
        if (roleRepository.existsByRoleName(roleName)){
            return roleRepository.findByRoleName(roleName);
        }
        throw new ApiException(HttpStatus.NOT_FOUND,"Role not found");
    }

    @Override
    public Role createRole(Role role) {
        if (roleRepository.existsByRoleName(role.getRoleName())){
            throw new ApiException(HttpStatus.CONFLICT,"Role already exists");
        }
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Role role) {
        return null;
    }

    @Override
    public void deleteRole(String roleName) {
        roleRepository.deleteByRoleName(roleName);
    }


}
