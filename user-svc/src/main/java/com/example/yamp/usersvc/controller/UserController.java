package com.example.yamp.usersvc.controller;

import com.example.yamp.usersvc.dto.customer.CustomerDto;
import com.example.yamp.usersvc.service.CustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.UUID;
import javax.security.auth.login.AccountNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@Slf4j
public class UserController {
  private final CustomerService customerService;

  public UserController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @SecurityRequirement(name = "oauth2")
  @GetMapping("/get-me")
  public CustomerDto getMe() throws AccountNotFoundException {
    return customerService.getCustomer();
  }

  @SecurityRequirement(name = "oauth2")
  @GetMapping("/{customerId}")
  public CustomerDto getCustomer(@PathVariable("customerId") UUID uuid)
      throws AccountNotFoundException {
    return customerService.getCustomer(uuid);
  }
}
