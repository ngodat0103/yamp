package org.example.authservice.service;


import jakarta.transaction.Transactional;
import org.example.authservice.dto.AccountDto;
import org.example.authservice.persistence.entity.Account;

import java.util.UUID;

public interface AccountService {


    AccountDto register(AccountDto accountDto) ;
    void addRole(UUID accountUuid, String roleName);

}
