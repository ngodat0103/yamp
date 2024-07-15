package com.example.userservice.service.impl;

import com.example.userservice.dto.model.RoleDto;
import com.example.userservice.dto.model.mapper.RoleMapper;
import com.example.userservice.entity.Role;
import com.example.userservice.exceptions.InvalidInputException;
import com.example.userservice.repositories.RoleRepository;
import com.example.userservice.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;
    private RoleMapper roleMapper ;
    @Override
    public RoleDto createRole(RoleDto roleDto) {
            Role new_role = roleMapper.modelToEntity(roleDto);
            if(roleRepository.existsByRoleName(roleDto.getRoleName()))
                throw new InvalidInputException("Role already exists");

            new_role = roleRepository.save(new_role);
            return roleMapper.entityToDto(new_role);

    }

    @Override
    public RoleDto getRole(Long roleID) {
        if(!roleRepository.existsByRoleID(roleID)){
            throw new InvalidInputException("Role not found");
        }
        return roleMapper.entityToDto(roleRepository.findByRoleID(roleID));
    }

    @Override
    public RoleDto getRole(String roleName) {
        if(!roleRepository.existsByRoleName(roleName)){
            throw new InvalidInputException("Role not found");
        }
        return roleMapper.entityToDto(roleRepository.findByRoleName(roleName));
    }

    @Override
    public RoleDto updateRole(RoleDto roleDto) {
        return null;
    }

    @Override
    @Transactional
    public void deleteRole(Long roleID) {
        if(roleRepository.existsByRoleID(roleID)){
            roleRepository.deleteByRoleID(roleID);
        }
        else throw new InvalidInputException("Role not found");

    }

    @Override
    public List<RoleDto> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        List<RoleDto> roleDtos = new ArrayList<>();
        for(Role role : roles){
            roleDtos.add(roleMapper.entityToDto(role));
        }
        return roleDtos;
    }
}
