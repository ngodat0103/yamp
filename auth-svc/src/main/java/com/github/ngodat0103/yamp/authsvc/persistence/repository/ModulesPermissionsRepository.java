package com.github.ngodat0103.yamp.authsvc.persistence.repository;

import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.ModulesPermissions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModulesPermissionsRepository extends JpaRepository<ModulesPermissions, Long> {}
