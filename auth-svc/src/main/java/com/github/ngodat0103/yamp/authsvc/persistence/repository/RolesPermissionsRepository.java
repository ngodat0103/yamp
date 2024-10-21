package com.github.ngodat0103.yamp.authsvc.persistence.repository;

import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.RolesPermissions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesPermissionsRepository extends JpaRepository<RolesPermissions, Integer> {}
