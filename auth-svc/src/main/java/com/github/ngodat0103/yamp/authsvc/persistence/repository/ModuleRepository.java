package com.github.ngodat0103.yamp.authsvc.persistence.repository;

import com.github.ngodat0103.yamp.authsvc.persistence.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Long> {
  boolean existsByName(String name);
}
