package org.example.authservice.controller;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.authservice.dto.AccountDto;
import org.example.authservice.entity.Account;
import org.example.authservice.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/account")
@RestController
@Tag(name = "Account" ,description = "Account API")
public class AccountController {
    private final AccountService accountService;
    private final static String ACCOUNT_UUID_HEADER = "X-Account-Uuid";

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
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
