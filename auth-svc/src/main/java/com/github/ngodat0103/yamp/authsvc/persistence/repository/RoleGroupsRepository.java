package com.github.ngodat0103.yamp.authsvc.persistence.repository;

import com.github.ngodat0103.yamp.authsvc.persistence.entity.roles.RoleGroups;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleGroupsRepository extends JpaRepository<RoleGroups, Integer> {}
