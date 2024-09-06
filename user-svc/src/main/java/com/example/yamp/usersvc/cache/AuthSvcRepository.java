package com.example.yamp.usersvc.cache;

import com.example.yamp.usersvc.persistence.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface AuthSvcRepository extends CrudRepository<Account, UUID> {
}
