package org.example.authservice.controller;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.impl.HMAC;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.authservice.config.Constants;
import org.example.authservice.dto.AccountDto;
import org.example.authservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collection;
import java.util.Date;


@RequestMapping("/account")
@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    public AccountController() throws InvalidKeySpecException, NoSuchAlgorithmException {
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto register(@Valid @RequestBody AccountDto account){
        return accountService.register(account);
    }

    @GetMapping("/login")
    public void login(UsernamePasswordAuthenticationToken authenticationToken, HttpServletResponse response) throws JOSEException {

        Collection<GrantedAuthority> authorities = authenticationToken.getAuthorities();
        StringBuilder authoritiesReturn = new StringBuilder();
        for(GrantedAuthority authority: authorities){
            authoritiesReturn.append(authority.getAuthority()).append(" ");
        }


        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .issuer("auth-service")
                .subject(authenticationToken.getName())
                .expirationTime(new Date(System.currentTimeMillis() + Constants.EXPIRATION_TIME))
                .claim("roles", authoritiesReturn.toString())
                .build();
        SignedJWT signedJWT = new SignedJWT( new JWSHeader(JWSAlgorithm.HS512),jwtClaimsSet);

        MACSigner macSigner = new MACSigner(Constants.secret.getBytes(StandardCharsets.UTF_8));
        signedJWT.sign(macSigner);

        response.addHeader("Authorization", "Bearer " + signedJWT.serialize());

    }



}
