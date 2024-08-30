package com.github.ngodat0103.yamp.authsvc.dto.mapper;

import com.github.ngodat0103.yamp.authsvc.dto.AccountDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "role",ignore = true)
    Account mapToEntity(AccountDto accountDto);

    @Mapping(target = "roleName",expression = "java(account.getRole().getRoleName())")
    AccountDto mapToDto(Account account);
}
