package org.example.authsvc.controller;
import com.github.ngodat0103.yamp.authsvc.dto.AccountDto;
import com.github.ngodat0103.yamp.authsvc.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(value = "/account",produces = "application/json")
@RestController
//@Hidden
public class AccountController {
    private final AccountService accountService;
    private final static String ACCOUNT_UUID_HEADER = "X-Account-Uuid";

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
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
