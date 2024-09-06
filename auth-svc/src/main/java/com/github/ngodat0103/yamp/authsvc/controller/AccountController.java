package com.github.ngodat0103.yamp.authsvc.controller;
import com.github.ngodat0103.yamp.authsvc.dto.AccountDto;
import com.github.ngodat0103.yamp.authsvc.dto.UpdateAccountDto;
import com.github.ngodat0103.yamp.authsvc.service.AccountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Set;
import java.util.UUID;




@Slf4j
@RequestMapping(value = "/accounts",produces = "application/json")
@RestController
@SecurityRequirement(name = "oauth2")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','SCOPE_auth-service.write')")
//@Hidden
public class AccountController {
    private final AccountService accountService;
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto register(@Valid @RequestBody AccountDto accountDto){
        log.debug("Controller register method called");
        return accountService.register(accountDto);
    }


    @GetMapping
    public Set<AccountDto > getAccounts() {
        log.debug("Controller getAccounts method called");
        return accountService.getAccounts();
    }

    @GetMapping(path = "/{accountUuid}")
    @PreAuthorize("hasAuthority('SCOPE_auth-service.read')")
    public AccountDto getAccount(@PathVariable UUID accountUuid) {
        log.debug("Controller getAccount method called");
        return accountService.getAccount(accountUuid);

    }

    @GetMapping(value = "/filter")
    public Set<AccountDto> getAccountsFilterByRoles(@RequestParam(required = false) Set<String> roles,
                                                    @RequestParam(required = false) UUID accountUuid,
                                                    @RequestParam(required = false) String username) {
            return accountService.getAccountFilter(roles, accountUuid, username);
    }


    @PutMapping()
    public AccountDto updateAccount(@RequestBody @Valid UpdateAccountDto updateAccountDto) {
        return accountService.updateAccount(updateAccountDto);
    }


}
