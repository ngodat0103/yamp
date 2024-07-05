package com.example.userservice.service;

import com.example.userservice.dto.model.RoleDto;

import java.util.List;

public interface RoleService {
    RoleDto createRole(RoleDto roleDto);
    RoleDto getRole(Long roleID);
    RoleDto getRole(String roleName);
    RoleDto updateRole(RoleDto roleDto);
    void deleteRole(Long roleID);
    List<RoleDto> getAllRoles();
}
