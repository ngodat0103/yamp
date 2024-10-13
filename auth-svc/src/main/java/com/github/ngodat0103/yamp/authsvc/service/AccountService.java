package com.github.ngodat0103.yamp.authsvc.service;

import com.github.ngodat0103.yamp.authsvc.dto.account.AccountRegisterDto;
import com.github.ngodat0103.yamp.authsvc.dto.account.AccountResponseDto;
import com.github.ngodat0103.yamp.authsvc.dto.account.UpdateAccountDto;
import java.util.Set;
import java.util.UUID;

public interface AccountService {
  AccountResponseDto register(AccountRegisterDto accountRegisterDto);

  AccountResponseDto updateAccount(UpdateAccountDto updateAccountDto);

  AccountResponseDto getAccount(UUID accountUuid);

  Set<AccountResponseDto> getAccounts();

  Set<AccountResponseDto> getAccountFilter(Set<String> roles, UUID accountUuid, String username);
}
