package org.example.authservice.service.impl;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.transaction.Transactional;
import org.example.authservice.dto.AccountDto;
import org.example.authservice.dto.mapper.AccountMapper;
import org.example.authservice.persistence.entity.Account;
import org.example.authservice.exception.ApiException;
import org.example.authservice.persistence.repository.AccountRepository;
import org.example.authservice.service.AccountService;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AccountServiceImpl.class);

    private static final String ROLE_NOT_FOUND = "Role not found!";
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    final PasswordEncoder passwordEncoder ;
    public AccountServiceImpl(AccountRepository accountRepository,
                              AccountMapper accountMapper,
                              PasswordEncoder passwordEncoder,
                              JWKSource<SecurityContext> jwkSource) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    @Override
    public AccountDto register(AccountDto accountDto) {

        if(accountRepository.existsById(accountDto.getAccountUuid()))
        {
            throw new ApiException(HttpStatus.CONFLICT,"AccountUuid is already exists!");
        }
        if(accountRepository.existsByUsername(accountDto.getUsername()))
        {
            throw new ApiException(HttpStatus.CONFLICT,"Username is already exists!");
        }
        if(accountRepository.existsByEmail(accountDto.getEmail())){
            throw new ApiException(HttpStatus.CONFLICT,"Email is already exists!");
        }
            Account account = accountMapper.mapToEntity(accountDto);
            Set<String> roles = new HashSet<>();
            roles.add("ROLE_CUSTOMER");
            account.setRoles(roles);
            Account savedAccount = accountRepository.save(account);
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
        throw new ApiException(HttpStatus.CONFLICT,"Role is already exists!");

    }

    private  Supplier<ApiException>  accountNotFoundSupplier(Object identifier) {
        {
            return () -> {
                if (identifier instanceof String username) {
                    logger.debug("account not found for username or email: {}", username);
                    throw new ApiException(HttpStatus.NOT_FOUND, "username or email not found");
                } else if (identifier instanceof UUID accountUuid) {
                    logger.debug("account not found for accountUuid: {}", accountUuid);
                    throw new ApiException(HttpStatus.NOT_FOUND, "accountUuid does not exist");
                } else {
                    // should never happen
                    logger.debug("account not found for identifier: {}", identifier);
                    throw new ApiException(HttpStatus.NOT_FOUND, "account not found");
                }
            };
        }
    }


}
