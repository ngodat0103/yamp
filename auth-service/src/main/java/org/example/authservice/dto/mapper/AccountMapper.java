package org.example.authservice.dto.mapper;

import lombok.AllArgsConstructor;
import org.example.authservice.dto.AccountDto;
import org.example.authservice.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "uuid", ignore = true)
    Account mapToEntity(AccountDto accountDto);
    AccountDto mapToDto(Account account);
}
