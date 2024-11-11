package com.github.ngodat0103.yamp.authsvc.persistence.repository;

import com.github.ngodat0103.yamp.authsvc.persistence.entity.role.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Boolean existsByName(String name);

  Optional<Role> findByName(String name);
}
