package org.example.authservice.controller;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.authservice.dto.AccountDto;
import org.example.authservice.dto.UserInfo;
import org.example.authservice.entity.Account;
import org.example.authservice.repository.AccountRepository;
import org.example.authservice.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContext;
import org.springframework.security.oauth2.server.authorization.token.*;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping(value = "/account",produces = "application/json")
@RestController
//@Hidden
public class AccountController {
    private final AccountService accountService;
    private final static String ACCOUNT_UUID_HEADER = "X-Account-Uuid";

    private final JwtEncoder jwtEncoder;


    private  final AccountRepository accountRepository ;

    public AccountController(AccountService accountService,
                             AccountRepository accountRepository,
                             JWKSource<SecurityContext> jwkSource
                             ) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.jwtEncoder = new NimbusJwtEncoder(jwkSource);

    }


    @PreAuthorize("hasAuthority('auth-service.read')")
    @GetMapping("/userinfo")
    public void getUserInfo(@RequestParam String username, HttpServletResponse response){
        Account account = accountRepository.findByUsername(username);
        if(account == null)
            throw new UsernameNotFoundException("Account not found");
        Set<String> roles = account.getAccountRole().stream()
                        .map(accountRole -> accountRole.getRole().getRoleName())
                        .collect(Collectors.toSet());
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuer("auth-service")
                .subject("user-info")
                .claims(c -> {
                    c.put("username",account.getUsername());
                    c.put("roles",roles);
                    c.put("password",account.getPassword());
                })
                .issuedAt(Instant.now())
                .issuedAt(Instant.now().plus(Duration.ofMinutes(1)))
                .build();

        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(jwtClaimsSet);
        Jwt jwt = jwtEncoder.encode(jwtEncoderParameters);


        response.addHeader("X-User-Info",jwt.getTokenValue());
    }



    @GetMapping()
    public AccountDto getAccount(@RequestHeader (value =ACCOUNT_UUID_HEADER) @Valid UUID accountUuid)
    {
        return accountService.getAccount(accountUuid);
    }

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody AccountDto account, HttpServletResponse response){
        Account newAccount =  accountService.register(account);
        response.addHeader(ACCOUNT_UUID_HEADER,newAccount.getAccountUuid().toString());
    }

    @PostMapping("/password")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updatePassword(
            @RequestHeader (value = ACCOUNT_UUID_HEADER) @Valid UUID accountUuid,
            @RequestHeader(value = "x-new-password") String newPassword)
    {
        accountService.patchPassword(accountUuid,newPassword);
    }
    @PostMapping("/email")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateEmail(
            @RequestHeader (value =ACCOUNT_UUID_HEADER) @Valid UUID accountUuid,
            @RequestHeader(value = "x-new-email") @Valid String newEmail)
    {
        accountService.patchEmail(accountUuid,newEmail);
    }

    @PostMapping("/role")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addRole(@RequestHeader (value = ACCOUNT_UUID_HEADER) @Valid UUID accountUuid,
                        @RequestHeader(value = "X-Role-Name") String roleName)
    {
        accountService.addRole(accountUuid,roleName);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@RequestHeader (value = "X-Account-Uuid") @Valid UUID accountUuid)
    {
        accountService.deleteAccount(accountUuid);
    }


}
