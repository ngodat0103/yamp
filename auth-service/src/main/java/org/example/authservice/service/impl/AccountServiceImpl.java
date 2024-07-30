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
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    private static final String ACCOUNT_NOT_FOUND = "Account not found!";
    private static final String ROLE_NOT_FOUND = "Role not found!";
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    final RoleRepository roleRepository;
    final PasswordEncoder passwordEncoder ;
    private final AccountRoleRepository accountRoleRepository;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AccountRoleRepository accountRoleRepository) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.accountRoleRepository = accountRoleRepository;
    }

    @Override
    public AccountDto getAccount(UUID accountUuid) {
        Account account = accountRepository.findByAccountUuid(accountUuid);
        if(account == null)
        {
            throw new ApiException(HttpStatus.NOT_FOUND,ACCOUNT_NOT_FOUND);
        }
        return accountMapper.mapToDto(account);
    }

    @Override
    public Account register(AccountDto accountDto) {

        if(accountRepository.existsByUsername(accountDto.getUsername()))
        {
            throw new ApiException(HttpStatus.UNPROCESSABLE_ENTITY,"Username is already exists!");
        }
        if(accountRepository.existsByEmail(accountDto.getEmail())){
            throw new ApiException(HttpStatus.UNPROCESSABLE_ENTITY,"Email is already exists!");
        }
            Account account = accountMapper.mapToEntity(accountDto);
            account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        return accountRepository.save(account);

    }

    @Override
    public void patchPassword(UUID accountUuid, String newPassword) {
        Account currentAccount = accountRepository.findByAccountUuid(accountUuid);
        if(currentAccount == null)
        {
            throw new ApiException(HttpStatus.NOT_FOUND,ACCOUNT_NOT_FOUND);
        }
        currentAccount.setPassword(passwordEncoder.encode(newPassword));
        accountRepository.save(currentAccount);
    }

    @Override
    public void patchEmail(UUID accountUuid, String newEmail) {
        Account currentAccount = accountRepository.findByAccountUuid(accountUuid);
        if(currentAccount == null)
        {
            throw new ApiException(HttpStatus.NOT_FOUND,ACCOUNT_NOT_FOUND);
        }
        currentAccount.setEmail(newEmail);
        accountRepository.save(currentAccount);
    }

    @Override
    public void deleteAccount(UUID accountUuid) {
        Account currentAccount = accountRepository.findByAccountUuid(accountUuid);
        if(currentAccount == null)
        {
            throw new ApiException(HttpStatus.NOT_FOUND,ACCOUNT_NOT_FOUND);
        }
        accountRepository.delete(currentAccount);
    }

    @Override
    public void addRole(UUID accountUuid, String roleName) {
        Account currentAccount = accountRepository.findByAccountUuid(accountUuid);
        if(currentAccount == null)
        {
            throw new ApiException(HttpStatus.NOT_FOUND,ACCOUNT_NOT_FOUND);
        }
        Role role = roleRepository.findByRoleName(roleName);
        if(role == null)
        {
            throw new ApiException(HttpStatus.NOT_FOUND,ROLE_NOT_FOUND);
        }
        AccountRole accountRole = new AccountRole(currentAccount,role);
        accountRoleRepository.save(accountRole);
    }

}
