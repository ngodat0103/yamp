package org.example.authservice.repository;

import org.example.authservice.entity.AccountRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface AccountRoleRepository extends CrudRepository<AccountRole, UUID> {
    public AccountRole save(AccountRole accountRole);
}
