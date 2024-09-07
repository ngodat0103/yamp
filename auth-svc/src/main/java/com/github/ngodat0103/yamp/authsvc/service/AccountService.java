package com.github.ngodat0103.yamp.authsvc.service;
import com.github.ngodat0103.yamp.authsvc.dto.RegisterAccountDto;
import com.github.ngodat0103.yamp.authsvc.dto.UpdateAccountDto;

import java.util.Set;
import java.util.UUID;

public interface AccountService {


    RegisterAccountDto register(RegisterAccountDto registerAccountDto) ;


    RegisterAccountDto updateAccount(UpdateAccountDto updateAccountDto) ;
    RegisterAccountDto getAccount(UUID accountUuid) ;
    Set<RegisterAccountDto> getAccounts() ;
    Set<RegisterAccountDto> getAccountFilter(Set<String> roles, UUID accountUuid, String username) ;

}
