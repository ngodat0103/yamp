package org.example.authservice.service;


import jakarta.transaction.Transactional;
import org.example.authservice.dto.AccountDto;
import org.example.authservice.persistence.entity.Account;

import java.util.UUID;

public interface AccountService {

    AccountDto getAccount(UUID accountUuid);

    // this return token jwt value issued by auth-service for gateway to authenticate and authorize
    String getUserDetails (String username);
    AccountDto register(AccountDto accountDto) ;
    void addRole(UUID accountUuid, String roleName);

}
