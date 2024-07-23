package org.example.authservice.service.impl;

import org.example.authservice.dto.AccountDto;
import org.example.authservice.dto.mapper.AccountMapper;
import org.example.authservice.entity.Account;
import org.example.authservice.entity.AccountRole;
import org.example.authservice.entity.Role;
import org.example.authservice.exception.ApiException;
import org.example.authservice.repository.AccountRepository;
import org.example.authservice.repository.AccountRoleRepository;
import org.example.authservice.repository.RoleRepository;
import org.example.authservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountMapper accountMapper;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder ;
    @Autowired
    private AccountRoleRepository accountRoleRepository;

    @Override
    public AccountDto register(AccountDto accountDto) {

        if(accountRepository.existsByUsername(accountDto.getUsername()))
        {
            throw new ApiException(HttpStatus.UNPROCESSABLE_ENTITY,"Username is already exists!");
        }
        Account account = accountMapper.mapToEntity(accountDto);
        account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        Account newAccount = accountRepository.save(account);
        Role role = roleRepository.findByRoleName("ROLE_USER");
        accountRoleRepository.save(new AccountRole(newAccount,role));
        return accountMapper.mapToDto(newAccount);
    }

    @Override
    public AccountDto getAccount(UUID uuid) {
        return null;
    }
}
