package com.github.ngodat0103.yamp.authsvc.service;
import com.github.ngodat0103.yamp.authsvc.dto.AccountDto;
import java.util.UUID;

public interface AccountService {


    AccountDto register(AccountDto accountDto) ;
    void addRole(UUID accountUuid, String roleName);

}
