package org.example.authservice.persistence.repository;

import org.example.authservice.persistence.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends CrudRepository<Role,UUID> {
    Role findByRoleName(String roleName);
    void deleteByRoleName(String roleName);
    boolean existsByRoleName(String roleName);
}
