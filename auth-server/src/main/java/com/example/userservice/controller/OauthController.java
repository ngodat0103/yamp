package com.example.userservice.controller;

import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OauthController {
    @GetMapping("/test/oauth")
    public String getOauth(OAuth2ClientAuthenticationToken token)
    {
        int stop = 0 ;
        return "got token";
    }
}
