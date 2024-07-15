package com.example.userservice.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping("/test")
    public String testOauth(@RequestHeader("Authorization") String token){
        int stop = 0 ;
        return "Oauth works!";
    }


}
