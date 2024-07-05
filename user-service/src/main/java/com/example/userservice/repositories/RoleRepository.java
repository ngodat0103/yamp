package com.example.userservice.repositories;

import com.example.userservice.dto.model.RoleDto;
import com.example.userservice.entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role,Long> {
    Role findByRoleID(Long roleID);
    Role findByRoleName(String roleName);
    List<Role> findAll();
    boolean existsByRoleName(String roleName);
    boolean existsByRoleID(Long roleID);
    Role save(Role role);
    void deleteByRoleID(Long roleID);

}
