package com.github.ngodat0103.yamp.authsvc.dto.mapper;

import com.github.ngodat0103.yamp.authsvc.dto.AccountDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "roles", ignore = true)
    Account mapToEntity(AccountDto accountDto);
    AccountDto mapToDto(Account account);
}
