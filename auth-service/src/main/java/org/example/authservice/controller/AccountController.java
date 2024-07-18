package org.example.authservice.controller;

import jakarta.validation.Valid;
import org.example.authservice.dto.AccountDto;
import org.example.authservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/account")
@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;
    @PostMapping("/register")
    @ResponseBody
    public AccountDto register(@Valid @RequestBody AccountDto account){
        return accountService.register(account);
    }
}
