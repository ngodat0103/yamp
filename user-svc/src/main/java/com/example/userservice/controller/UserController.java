package com.example.userservice.controller;

import com.example.userservice.dto.customer.CustomerDto;
import com.example.userservice.dto.customer.CustomerRegisterDto;
import com.example.userservice.service.CustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.security.Principal;
import java.util.UUID;

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
    public void register(@RequestBody @Valid CustomerRegisterDto customerRegisterDto, HttpServletRequest request) {
        String correlationId = request.getHeader(CORRELATION_ID_HEADER);
        customerService.register(customerRegisterDto,correlationId);
    }

    @SecurityRequirement(name = "http-basic")
    @GetMapping( "/get-me")
    public CustomerDto getMe(JwtAuthenticationToken authenticationToken, HttpServletRequest request) throws AccountNotFoundException {
        if(authenticationToken == null){
            throw new AccountNotFoundException("Account not found");
        }
        Jwt jwt = authenticationToken.getToken();
        String xCorrelationId = request.getHeader(CORRELATION_ID_HEADER);
        return customerService.getCustomer(jwt,xCorrelationId);

    }


}
