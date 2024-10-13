package com.github.ngodat0103.yamp.authsvc.controller;

import com.github.ngodat0103.yamp.authsvc.dto.account.AccountRegisterDto;
import com.github.ngodat0103.yamp.authsvc.dto.account.AccountResponseDto;
import com.github.ngodat0103.yamp.authsvc.dto.account.UpdateAccountDto;
import com.github.ngodat0103.yamp.authsvc.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Set;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping(value = "/accounts", produces = "application/json")
@RestController
@SecurityRequirement(name = "oauth2")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','SCOPE_auth-service.write')")
public class AccountController {
  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @Operation(
      summary = "Register a new account",
      description = "Creates a new account with the provided details.")
  @ApiResponse(responseCode = "201", description = "Account created successfully")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public AccountResponseDto register(@Valid @RequestBody AccountRegisterDto accountRegisterDto) {
    log.debug("Controller register method called");
    return accountService.register(accountRegisterDto);
  }

  @Operation(summary = "Get all accounts", description = "Retrieves a list of all accounts.")
  @GetMapping
  public Set<AccountResponseDto> getAccounts() {
    log.debug("Controller getAccounts method called");
    return accountService.getAccounts();
  }

  @Operation(
      summary = "Get account by UUID",
      description = "Retrieves account information by its unique identifier.")
  @ApiResponse(responseCode = "200", description = "Account retrieved successfully")
  @GetMapping(path = "/{accountUuid}")
  @PreAuthorize("hasAuthority('SCOPE_auth-service.read')")
  public AccountResponseDto getAccount(@PathVariable UUID accountUuid) {
    log.debug("Controller getAccount method called");
    return accountService.getAccount(accountUuid);
  }

  @Operation(
      summary = "Get accounts by filter",
      description = "Retrieves a list of accounts based on specified criteria.")
  @GetMapping(value = "/filter")
  public Set<AccountResponseDto> getAccountsFilterByRoles(
      @Parameter(description = "Set of roles to filter accounts") @RequestParam(required = false)
          Set<String> roles,
      @Parameter(description = "Account UUID to filter") @RequestParam(required = false)
          UUID accountUuid,
      @Parameter(description = "Username to filter") @RequestParam(required = false)
          String username) {
    return accountService.getAccountFilter(roles, accountUuid, username);
  }

  @Operation(
      summary = "Update account",
      description = "Modifies an existing account's information.")
  @PutMapping()
  public AccountResponseDto updateAccount(@RequestBody @Valid UpdateAccountDto updateAccountDto) {
    return accountService.updateAccount(updateAccountDto);
  }

  @Operation(
      summary = "Logout",
      description = "Logs out the current user and invalidates the session.")
  @DeleteMapping(path = "/logout")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void logout(HttpServletRequest request) {
    request.getSession().invalidate();
    SecurityContextHolder.clearContext();
  }
}
