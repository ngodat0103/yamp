package com.example.userservice.controller;

import com.example.userservice.dto.CustomerDto;
import com.example.userservice.dto.RegisterDto;
import com.example.userservice.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;

@RestController
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

    @GetMapping( "/getMe")
    public CustomerDto getMe(@RequestHeader(value = "X-Account-Uuid") UUID xAccountUuid) throws AccountNotFoundException {
        return customerService.getCustomer(xAccountUuid);

    }


}
