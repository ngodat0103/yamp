package com.github.ngodat0103.yamp.authsvc.service;

import com.github.ngodat0103.yamp.authsvc.dto.RoleDto;

import java.util.Set;

public interface RoleService {


    Set<RoleDto> getRole();
    void addRole(RoleDto roleDto);
    void updateRole(RoleDto roleDto);
    void deleteRole(String roleName);

}
