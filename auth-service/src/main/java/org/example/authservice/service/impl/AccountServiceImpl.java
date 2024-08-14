package org.example.authservice.service.impl;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.transaction.Transactional;
import org.example.authservice.dto.AccountDto;
import org.example.authservice.dto.mapper.AccountMapper;
import org.example.authservice.persistence.entity.Account;
import org.example.authservice.persistence.entity.AccountRole;
import org.example.authservice.persistence.entity.Role;
import org.example.authservice.exception.ApiException;
import org.example.authservice.persistence.repository.AccountRepository;
import org.example.authservice.persistence.repository.AccountRoleRepository;
import org.example.authservice.persistence.repository.RoleRepository;
import org.example.authservice.service.AccountService;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AccountServiceImpl.class);

    private static final String ROLE_NOT_FOUND = "Role not found!";
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    final RoleRepository roleRepository;
    final PasswordEncoder passwordEncoder ;
    private final JwtEncoder jwtEncoder;
    private final AccountRoleRepository accountRoleRepository;
    public AccountServiceImpl(AccountRepository accountRepository,
                              AccountMapper accountMapper,
                              RoleRepository roleRepository,
                              PasswordEncoder passwordEncoder,
                              JWKSource<SecurityContext> jwkSource,
                              AccountRoleRepository accountRoleRepository) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = new NimbusJwtEncoder(jwkSource);
        this.accountRoleRepository = accountRoleRepository;
    }

    @Override
    public AccountDto getAccount(UUID accountUuid) {
        Account account = accountRepository.findById(accountUuid)
                .orElseThrow(accountNotFoundSupplier(accountUuid.toString()));
        return accountMapper.mapToDto(account);
    }

    @Override
    public String getUserDetails(String username) {
        Account account = accountRepository
                .findByUsernameOrEmail(username,username)
                .orElseThrow(accountNotFoundSupplier(username));
        Set<String> roles = account.getAccountRole().stream()
                .map(accountRole -> accountRole.getRole().getRoleName())
                .collect(Collectors.toSet());
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuer("auth-service")
                .subject("user-details")
                .claims(c -> {
                    c.put("username",account.getUsername());
                    c.put("roles",roles);
                    c.put("password",account.getPassword());
                    c.put("accountUuid",account.getAccountUuid().toString());
                })
                .issuedAt(Instant.now())
                .issuedAt(Instant.now().plus(Duration.ofMinutes(1)))
                .build();

        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(jwtClaimsSet);
        Jwt jwt = jwtEncoder.encode(jwtEncoderParameters);
        return jwt.getTokenValue();
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
            Account savedAccount = accountRepository.save(account);
            return accountMapper.mapToDto(savedAccount);
    }


    @Override
    public void addRole(UUID accountUuid, String roleName) {
        Account currentAccount = accountRepository.findById(accountUuid)
                .orElseThrow(accountNotFoundSupplier(accountUuid));
        Role role = roleRepository.findByRoleName(roleName);
        if(role == null)
        {
            throw new ApiException(HttpStatus.NOT_FOUND,ROLE_NOT_FOUND);
        }
        AccountRole accountRole = new AccountRole(currentAccount,role);
        accountRoleRepository.save(accountRole);
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
