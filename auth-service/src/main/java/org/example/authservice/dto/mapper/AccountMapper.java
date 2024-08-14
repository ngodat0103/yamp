package org.example.authservice.dto.mapper;

import org.example.authservice.dto.AccountDto;
import org.example.authservice.persistence.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "accountRole",ignore = true)
    Account mapToEntity(AccountDto accountDto);
    AccountDto mapToDto(Account account);
}
