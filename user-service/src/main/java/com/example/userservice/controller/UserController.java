package com.example.userservice.controller;

import com.example.userservice.dto.CustomerDto;
import com.example.userservice.dto.RegisterDto;
import com.example.userservice.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    private  final CustomerService customerService;

    public UserController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid RegisterDto registerDto){
        customerService.register(registerDto);
    }

    @SecurityRequirement(name = "oauth2")
    @GetMapping( "/getMe")
    public CustomerDto getMe(@RequestHeader(value = "X-Account-Uuid",required = false) UUID xAccountUuid) throws AccountNotFoundException {
        return customerService.getCustomer(xAccountUuid);

    }


}
