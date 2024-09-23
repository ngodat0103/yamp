package com.github.ngodat0103.yamp.authsvc.service.impl;

import static com.github.ngodat0103.yamp.authsvc.Util.*;

import com.github.ngodat0103.yamp.authsvc.dto.account.AccountRequestDto;
import com.github.ngodat0103.yamp.authsvc.dto.account.AccountResponseDto;
import com.github.ngodat0103.yamp.authsvc.dto.account.UpdateAccountDto;
import com.github.ngodat0103.yamp.authsvc.dto.kafka.Action;
import com.github.ngodat0103.yamp.authsvc.dto.mapper.AccountMapper;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Account;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Role;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.AccountRepository;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.RoleRepository;
import com.github.ngodat0103.yamp.authsvc.service.AccountService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;
  private final AccountMapper accountMapper;
  private final PasswordEncoder passwordEncoder;
  private final RoleRepository roleRepository;
  private final KafkaTemplate<UUID, Action> kafkaTemplate;

  @Transactional
  @Override
  public AccountResponseDto register(AccountRequestDto accountRequestDto) {
    Account account = accountMapper.mapToEntity(accountRequestDto);

    if (accountRepository.existsById(account.getUuid())) {
      throwConflictException(log, "Account", "uuid", account.getUuid());
    }
    if (accountRepository.existsByUsername(account.getUsername())) {
      throwConflictException(log, "Account", "username", account.getUsername());
    }
    if (accountRepository.existsByEmail(account.getEmail())) {
      throwConflictException(log, "Account", "email", account.getEmail());
    }

    account.setPassword(passwordEncoder.encode(account.getPassword()));
    String roleName = accountRequestDto.getRoleName().toUpperCase();
    Role role =
        roleRepository
            .findRoleByRoleName(roleName)
            .orElseThrow(notFoundExceptionSupplier(log, "Role", "roleName", roleName));
    account.setRole(role);

    LocalDateTime currentTime = LocalDateTime.now();
    account.setCreateAt(currentTime);
    account.setLastModifiedAt(currentTime);
    Account savedAccount = accountRepository.save(account);

    return accountMapper.mapToDto(savedAccount);
  }

  @Override
  @Transactional
  public AccountResponseDto updateAccount(UpdateAccountDto updateAccountDto) {
    log.debug("Getting accountUuid from SecurityContextHolder");
    String roleName = updateAccountDto.getRoleName().toUpperCase();
    UUID uuid = getAccountUuidFromAuthentication();
    Account account =
        accountRepository
            .findById(uuid)
            .orElseThrow(notFoundExceptionSupplier(log, "Account", "accountUuid", uuid));
    account.setEmail(updateAccountDto.getEmail());
    account.setUsername(updateAccountDto.getUsername());
    account.setRole(
        roleRepository
            .findRoleByRoleName(roleName)
            .orElseThrow(notFoundExceptionSupplier(log, "Role", "roleName", roleName)));

    account.setLastModifiedAt(LocalDateTime.now());
    Account savedAccount = accountRepository.save(account);

    kafkaTemplate.send("account-update", savedAccount.getUuid(), Action.UPDATE);
    return accountMapper.mapToDto(savedAccount);
  }

  @Override
  public AccountResponseDto getAccount(UUID accountUuid) {
    log.debug("Getting account by accountUuid");
    Account account =
        accountRepository
            .findById(accountUuid)
            .orElseThrow(notFoundExceptionSupplier(log, "Account", "accountUuid", accountUuid));
    return accountMapper.mapToDto(account);
  }

  @Override
  public Set<AccountResponseDto> getAccounts() {
    log.debug("Getting all accounts");
    return accountRepository.findAll().stream()
        .map(accountMapper::mapToDto)
        .collect(Collectors.toUnmodifiableSet());
  }

  @Override
  public Set<AccountResponseDto> getAccountFilter(
      Set<String> roles, UUID accountUuid, String username) {
    log.debug("Getting accounts with filter");
    if (accountUuid != null) {
      Account account =
          accountRepository
              .findById(accountUuid)
              .orElseThrow(notFoundExceptionSupplier(log, "Account", "accountUuid", accountUuid));
      return Set.of(accountMapper.mapToDto(account));
    } else if (username != null) {
      Account account =
          accountRepository
              .findByUsername(username)
              .orElseThrow(notFoundExceptionSupplier(log, "Account", "username", username));
      return Set.of(accountMapper.mapToDto(account));
    } else {
      roles = roles.stream().map(String::toUpperCase).collect(Collectors.toUnmodifiableSet());
      return roleRepository.findRolesByRoleNameIn(roles).stream()
          .map(Role::getAccounts)
          .flatMap(Set::stream)
          .map(accountMapper::mapToDto)
          .collect(Collectors.toUnmodifiableSet());
    }
  }
}
