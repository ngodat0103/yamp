package com.example.userservice;

import com.example.userservice.dto.customer.CustomerDto;
import com.example.userservice.dto.customer.CustomerRegisterDto;
import com.example.userservice.service.CustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public CustomerDto getMe(HttpServletRequest request) throws AccountNotFoundException {
        Principal principal = request.getUserPrincipal();
        if(principal == null){
            throw new AccountNotFoundException("Account not found");
        }
        String accountUuid = principal.getName();
        String xCorrelationId = request.getHeader(CORRELATION_ID_HEADER);


        if (accountUuid == null) {
            throw new AccountNotFoundException("Account not found");
        }
        return customerService.getCustomer(UUID.fromString(accountUuid),xCorrelationId);

    }


}
