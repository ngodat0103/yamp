package com.github.ngodat0103.yamp.authsvc.persistence.repository;

import com.github.ngodat0103.yamp.authsvc.persistence.entity.module.Operations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationsRepository extends JpaRepository<Operations, Long> {}
