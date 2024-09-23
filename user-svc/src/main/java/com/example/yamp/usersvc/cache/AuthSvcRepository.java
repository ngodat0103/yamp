package com.example.yamp.usersvc.cache;

import com.example.yamp.usersvc.persistence.entity.Account;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthSvcRepository extends CrudRepository<Account, UUID> {}
