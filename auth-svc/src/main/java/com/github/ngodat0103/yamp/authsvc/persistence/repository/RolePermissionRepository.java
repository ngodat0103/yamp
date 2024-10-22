package com.github.ngodat0103.yamp.authsvc.persistence.repository;

import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.RolePermission;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.RolePermissionId;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionId> {
  Set<RolePermission> findByRoleId(Long roleId);
}
