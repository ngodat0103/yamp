package org.example.authservice.controller;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.authservice.dto.AccountDto;
import org.example.authservice.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@RequestMapping("/account")
@RestController
@Tag(name = "Account" ,description = "Account API")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto register(@Valid @RequestBody AccountDto account){
        return accountService.register(account);
    }


}
