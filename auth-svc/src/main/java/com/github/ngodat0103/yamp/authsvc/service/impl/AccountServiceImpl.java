package com.github.ngodat0103.yamp.authsvc.service.impl;
import com.github.ngodat0103.yamp.authsvc.dto.AccountDto;
import com.github.ngodat0103.yamp.authsvc.dto.mapper.AccountMapper;
import com.github.ngodat0103.yamp.authsvc.exception.ConflictException;
import com.github.ngodat0103.yamp.authsvc.exception.NotFoundException;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Account;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Role;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.AccountRepository;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.RoleRepository;
import com.github.ngodat0103.yamp.authsvc.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    @Transactional
    @Override
    public AccountDto register(AccountDto accountDto) {
        Account account = accountMapper.mapToEntity(accountDto);

        if(accountRepository.existsById(account.getAccountUuid()))
        {
            log.debug("AccountUuid is already exists!");
            throw new ConflictException("AccountUuid is already exists!");
        }
        if(accountRepository.existsByUsername(account.getUsername()))
        {
            log.debug("Username is already exists!");
            throw new ConflictException("username is already exists!");
        }
        if(accountRepository.existsByEmail(account.getEmail())){
            log.debug("Email is already exists!");
            throw new ConflictException("Email is already exists!");
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        String roleName = accountDto.getRoleName();
        Role  role  = roleRepository.findRoleByRoleName(roleName).orElseThrow(roleNotFoundSupplier(roleName));
        account.setRole(role);
        Account savedAccount = accountRepository.save(account);
        return accountMapper.mapToDto(savedAccount);
    }

    @Override
    public Set<AccountDto> getAccounts() {
        log.debug("Getting all accounts");
     return  accountRepository.findAll().stream().map(account -> {
                    AccountDto accountDto = accountMapper.mapToDto(account);
                    accountDto.setRoleName(account.getRole().getRoleName());
                    return accountDto;
                }).collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Set<AccountDto> getAccountFilter(Set<String> roles, UUID accountUuid, String username) {


        log.debug("Getting accounts with filter");
        if(accountUuid!=null){
            Account account = accountRepository.findById(accountUuid).orElseThrow(accountNotFoundSupplier(accountUuid));
            return Set.of(accountMapper.mapToDto(account));
        }
        else if(username!=null){
            Account account = accountRepository.findByUsername(username).orElseThrow(accountNotFoundSupplier(username));
            return Set.of(accountMapper.mapToDto(account));
        }
        else {
           roles = roles.stream().map(String::toUpperCase).collect(Collectors.toUnmodifiableSet());

          return roleRepository.findRolesByRoleNameIn(roles).stream()
                  .map(Role::getAccounts)
                  .flatMap(Set::stream)
                  .map(accountMapper::mapToDto)
                  .collect(Collectors.toUnmodifiableSet());

        }
    }


    private Supplier<NotFoundException> accountNotFoundSupplier(Object identifier) {
        {
            return () -> {
                if (identifier instanceof String username) {
                    log.debug("account not found for username or email: {}", username);
                    throw new NotFoundException("username or email not found");
                } else if (identifier instanceof UUID accountUuid) {
                    log.debug("account not found for accountUuid: {}", accountUuid);
                    throw new NotFoundException("accountUuid does not exist");
                } else {
                    // should never happen
                    log.debug("account not found for identifier: {}", identifier);
                    throw new NotFoundException("account not found");
                }
            };
        }
    }

    private Supplier<NotFoundException> roleNotFoundSupplier(String roleName){
        return () -> {
            log.debug("Role not found for roleName: {}", roleName);
            throw new NotFoundException("Role not found");
        };
    }

}
