package com.github.ngodat0103.yamp.authsvc.service;
import com.github.ngodat0103.yamp.authsvc.dto.AccountDto;

import java.util.Set;
import java.util.UUID;

public interface AccountService {


    AccountDto register(AccountDto accountDto) ;

    Set<AccountDto> getAccounts() ;
    Set<AccountDto> getAccountFilter(Set<String> roles, UUID accountUuid, String username) ;

}
