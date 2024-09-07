package com.github.ngodat0103.yamp.authsvc.dto.mapper;

import com.github.ngodat0103.yamp.authsvc.dto.RegisterAccountDto;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {
    Account mapToEntity(RegisterAccountDto registerAccountDto);
    @Mapping(target = "roleName",expression = "java(account.getRole().getRoleName())")
    RegisterAccountDto mapToDto(Account account);

}
