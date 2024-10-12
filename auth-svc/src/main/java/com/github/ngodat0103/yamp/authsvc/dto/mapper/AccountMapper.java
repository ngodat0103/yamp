package com.github.ngodat0103.yamp.authsvc.dto.mapper;

import com.github.ngodat0103.yamp.authsvc.dto.account.AccountRegisterDto;
import com.github.ngodat0103.yamp.authsvc.dto.account.AccountResponseDto;
import com.github.ngodat0103.yamp.authsvc.dto.account.UpdateAccountDto;
import com.github.ngodat0103.yamp.authsvc.dto.kafka.AccountTopicContent;
import com.github.ngodat0103.yamp.authsvc.dto.kafka.Action;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {
  Account mapToEntity(AccountRegisterDto accountRegisterDto);

  @Mapping(target = "roleName", expression = "java(account.getRole().getRoleName())")
  AccountResponseDto mapToDto(Account account);

  AccountTopicContent mapToTopicContent(AccountRegisterDto accountRegisterDto, Action action);
  AccountTopicContent mapToTopicContent(UpdateAccountDto updateAccountDto, Action action);
}
