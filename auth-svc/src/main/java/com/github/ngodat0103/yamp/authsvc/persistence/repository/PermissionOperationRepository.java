package com.github.ngodat0103.yamp.authsvc.persistence.repository;

import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.PermissionOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionOperationRepository extends JpaRepository<PermissionOperation, Long> {}
