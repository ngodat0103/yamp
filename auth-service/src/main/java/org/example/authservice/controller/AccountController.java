package org.example.authservice.controller;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.authservice.dto.AccountDto;
import org.example.authservice.persistence.entity.Account;
import org.example.authservice.persistence.repository.AccountRepository;
import org.example.authservice.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.*;
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

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/user-details")
    public void getUserInfo(@RequestParam String username, HttpServletResponse response){
        String jwtToken = accountService.getUserDetails(username);
        response.addHeader("X-Jwt-Token", jwtToken);
    }



    @GetMapping()
    public AccountDto getAccount(@RequestHeader (value =ACCOUNT_UUID_HEADER) @Valid UUID accountUuid)
    {
        return accountService.getAccount(accountUuid);
    }

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto register(@Valid @RequestBody AccountDto accountDto){
        return accountService.register(accountDto);
    }



    @PostMapping("/role")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addRole(@RequestHeader (value = ACCOUNT_UUID_HEADER) @Valid UUID accountUuid,
                        @RequestHeader(value = "X-Role-Name") String roleName)
    {
        accountService.addRole(accountUuid,roleName);
    }

}
