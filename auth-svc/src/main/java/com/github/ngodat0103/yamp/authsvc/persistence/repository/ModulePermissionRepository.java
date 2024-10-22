package com.github.ngodat0103.yamp.authsvc.persistence.repository;

import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.ModulePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModulePermissionRepository extends JpaRepository<ModulePermission, Long> {}
