package com.example.yamp.usersvc.controller;

import com.example.yamp.usersvc.dto.customer.CustomerDto;
import com.example.yamp.usersvc.dto.customer.CustomerRegisterDto;
import com.example.yamp.usersvc.service.CustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import javax.security.auth.login.AccountNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@Slf4j
public class UserController {
  private final CustomerService customerService;

  public UserController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public void register(@RequestBody @Valid CustomerRegisterDto customerRegisterDto) {
    log.debug("Controller register method called");
    customerService.register(customerRegisterDto);
  }

  @SecurityRequirement(name = "oauth2")
  @GetMapping("/get-me")
  public CustomerDto getMe() throws AccountNotFoundException {
    return customerService.getCustomer();
  }
}
