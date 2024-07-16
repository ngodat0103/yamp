package com.example.userservice.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping( "/test")
    public String testOauth(Authentication token){
        Jwt jwt_token = (Jwt) token.getPrincipal();
        int stop = 0 ;
        return "Oauth works!";
    }


}
