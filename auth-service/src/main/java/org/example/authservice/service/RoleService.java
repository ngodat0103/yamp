package org.example.authservice.service;

import org.example.authservice.persistence.entity.Role;

public interface RoleService {
    public Role getRole(String roleName);
    public Role createRole(Role role);
    public Role updateRole(Role role);
    public void deleteRole(String roleName);
}
