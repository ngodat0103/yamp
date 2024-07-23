package org.example.authservice.repository;

import org.example.authservice.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends CrudRepository<Role,UUID> {
    Role findByRoleName(String roleName);
    Role save(Role role);
    void deleteByRoleName(String roleName);
    boolean existsByRoleName(String roleName);
}
