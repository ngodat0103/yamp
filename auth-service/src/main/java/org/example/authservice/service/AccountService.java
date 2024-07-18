package org.example.authservice.service;


import org.example.authservice.dto.AccountDto;

import java.util.UUID;

public interface AccountService {

    AccountDto register(AccountDto accountDto) ;
    AccountDto getAccount(UUID uuid);

}
