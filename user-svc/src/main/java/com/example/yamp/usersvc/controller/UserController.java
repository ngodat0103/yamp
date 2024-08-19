package com.example.yamp.usersvc.controller;

import com.example.yamp.usersvc.dto.customer.CustomerDto;
import com.example.yamp.usersvc.dto.customer.CustomerRegisterDto;
import com.example.yamp.usersvc.service.CustomerService;
import com.example.yamp.usersvc.constant.AuthServiceUri;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;

@RestController
@RequestMapping()
@Slf4j
public class UserController {
    private  final CustomerService customerService;

    public UserController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid CustomerRegisterDto customerRegisterDto) {
        log.debug("Controller register method called");
        customerService.register(customerRegisterDto);
    }

    @SecurityRequirement(name = "http-basic")
    @GetMapping( "/get-me")
    public CustomerDto getMe(JwtAuthenticationToken authenticationToken, HttpServletRequest request) throws AccountNotFoundException {
        if(authenticationToken == null){
            throw new AccountNotFoundException("Account not found");
        }
        Jwt jwt = authenticationToken.getToken();
        String xCorrelationId = request.getHeader(AuthServiceUri.CORRELATION_ID_HEADER);
        return customerService.getCustomer(jwt,xCorrelationId);

    }


}
