package com.example.userservice.controller;

import com.example.userservice.dto.CustomerDto;
import com.example.userservice.dto.RegisterDto;
import com.example.userservice.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;

import static com.example.userservice.constant.AuthServiceUri.ACCOUNT_UUID_HEADER;
import static com.example.userservice.constant.AuthServiceUri.CORRELATION_ID_HEADER;

@RestController
@RequestMapping()
public class UserController {
    private  final CustomerService customerService;

    public UserController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid RegisterDto registerDto, HttpServletRequest request) {
        String correlationId = request.getHeader(CORRELATION_ID_HEADER);
        customerService.register(registerDto,correlationId);
    }

    @SecurityRequirement(name = "oauth2")
    @GetMapping( "/getMe")
    public CustomerDto getMe(HttpServletRequest httpServletRequest) throws AccountNotFoundException {
        String xAccountUuid = httpServletRequest.getHeader(ACCOUNT_UUID_HEADER);
        String xCorrelationId = httpServletRequest.getHeader(CORRELATION_ID_HEADER);
        if (xAccountUuid == null) {
            throw new AccountNotFoundException("Account not found");
        }
        return customerService.getCustomer(UUID.fromString(xAccountUuid),xCorrelationId);

    }


}
