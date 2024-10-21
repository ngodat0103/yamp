package com.github.ngodat0103.yamp.authsvc.persistence.repository;

import com.github.ngodat0103.yamp.authsvc.persistence.entity.permission.PermissionsOperations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionsOperationsRepository
    extends JpaRepository<PermissionsOperations, Long> {}
