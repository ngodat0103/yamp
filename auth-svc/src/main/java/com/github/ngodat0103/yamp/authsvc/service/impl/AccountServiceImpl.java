package com.github.ngodat0103.yamp.authsvc.service.impl;
import com.github.ngodat0103.yamp.authsvc.dto.AccountDto;
import com.github.ngodat0103.yamp.authsvc.dto.mapper.AccountMapper;
import com.github.ngodat0103.yamp.authsvc.exception.ConflictException;
import com.github.ngodat0103.yamp.authsvc.exception.NotFoundException;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Account;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.AccountRepository;
import com.github.ngodat0103.yamp.authsvc.service.AccountService;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
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
            Set<String> roles = new HashSet<>();
            roles.add("ROLE_CUSTOMER");
            account.setRoles(roles);
            log.debug("Set roles for new account");
            Account savedAccount = accountRepository.save(account);
            log.debug("Account saved successfully");
            return accountMapper.mapToDto(savedAccount);
    }


    @Override
    public void addRole(UUID accountUuid, String roleName) {
        Account currentAccount = accountRepository.findById(accountUuid)
                .orElseThrow(accountNotFoundSupplier(accountUuid));

        Set<String> roles = currentAccount.getRoles();
        if (!roles.contains(roleName)) {
            roles.add("ROLE_" + roleName.toUpperCase());
            currentAccount.setRoles(roles);
            accountRepository.save(currentAccount);
            return;
        }
        throw new ConflictException("Role is already exists!");

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


}
