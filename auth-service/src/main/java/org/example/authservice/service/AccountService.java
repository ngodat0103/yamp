package org.example.authservice.service;


import jakarta.transaction.Transactional;
import org.example.authservice.dto.AccountDto;
import org.example.authservice.entity.Account;

import java.util.UUID;

public interface AccountService {
    @Transactional(rollbackOn = NullPointerException.class)
    Account register(AccountDto accountDto) ;
    void patchPassword(UUID accountUuid, String newPassword);
    void patchEmail(UUID accountUuid, String newEmail);
    void deleteAccount(UUID accountUuid);
    void addRole(UUID accountUuid, String roleName);

}
