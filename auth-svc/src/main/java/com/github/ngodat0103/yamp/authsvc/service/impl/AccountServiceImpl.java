package com.github.ngodat0103.yamp.authsvc.service.impl;
import com.github.ngodat0103.yamp.authsvc.dto.AccountDto;
import com.github.ngodat0103.yamp.authsvc.dto.UpdateAccountDto;
import com.github.ngodat0103.yamp.authsvc.dto.mapper.AccountMapper;
import com.github.ngodat0103.yamp.authsvc.event.Action;
import com.github.ngodat0103.yamp.authsvc.event.Model;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Account;
import com.github.ngodat0103.yamp.authsvc.persistence.entity.Role;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.AccountRepository;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.RoleRepository;
import com.github.ngodat0103.yamp.authsvc.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import static com.github.ngodat0103.yamp.authsvc.Util.*;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {


    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final KafkaTemplate<String, Model> kafkaTemplate;
    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper, PasswordEncoder passwordEncoder, RoleRepository roleRepository, KafkaTemplate<String, Model> kafkaTemplate) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.kafkaTemplate = kafkaTemplate;
    }


    @Transactional
    @Override
    public AccountDto register(AccountDto accountDto) {
        Account account = accountMapper.mapToEntity(accountDto);

        if(accountRepository.existsById(account.getUuid()))
        {
           throwConflictException(log,"Account","accountUuid",account.getUuid());
        }
        if(accountRepository.existsByUsername(account.getUsername()))
        {
            throwConflictException(log,"Account","username",account.getUsername());
        }
        if(accountRepository.existsByEmail(account.getEmail())){
            throwConflictException(log,"Account","email",account.getEmail());
        }

        account.setPassword(passwordEncoder.encode(account.getPassword()));
        String roleName = accountDto.getRoleName();
        Role  role  = roleRepository.findRoleByRoleName(roleName)
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
    public AccountDto updateAccount(UpdateAccountDto updateAccountDto) {
        log.debug("Getting accountUuid from SecurityContextHolder");
        UUID uuid = getAccountUuidFromAuthentication();
        Account account = accountRepository.findById(uuid)
                .orElseThrow(notFoundExceptionSupplier(log, "Account", "accountUuid", uuid));
        account.setEmail(updateAccountDto.getEmail());
        account.setUsername(updateAccountDto.getUsername());
        account.setRole(roleRepository.findRoleByRoleName(updateAccountDto.getRoleName().toUpperCase())
                .orElseThrow(notFoundExceptionSupplier(log, "Role", "roleName", updateAccountDto.getRoleName())));

        account.setLastModifiedAt(LocalDateTime.now());
        Account savedAccount = accountRepository.save(account);
        Model model = new Model(Action.CREATE, savedAccount.getUuid(), savedAccount.getCreateAt(), savedAccount.getLastModifiedAt());
        kafkaTemplate.send("account-update", model);

        return accountMapper.mapToDto(savedAccount);
    }

    @Override
    public AccountDto getAccount(UUID accountUuid) {
        log.debug("Getting account by accountUuid");
        Account account = accountRepository.findById(accountUuid)
                .orElseThrow(notFoundExceptionSupplier(log, "Account", "accountUuid", accountUuid));
        return accountMapper.mapToDto(account);
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
            Account account = accountRepository.findById(accountUuid)
                    .orElseThrow(notFoundExceptionSupplier(log, "Account", "accountUuid", accountUuid));
            return Set.of(accountMapper.mapToDto(account));
        }
        else if(username!=null){
            Account account = accountRepository.findByUsername(username)
                    .orElseThrow(notFoundExceptionSupplier(log, "Account", "username", username));
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

}
