package com.github.ngodat0103.yamp.authsvc.authserver;

import com.github.ngodat0103.yamp.authsvc.persistence.entity.Account;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.AccountRepository;
import com.github.ngodat0103.yamp.authsvc.vault.HcpVault;
import com.github.ngodat0103.yamp.authsvc.vault.HcpVaultConfiguration;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.text.ParseException;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
public class Oauth2Configuration {
    @Bean
    JWKSource<SecurityContext> jwkSourceFromHcpVault(ApplicationContext ctx) throws ParseException, JOSEException {
    HcpVault hcpVault;
        try{
            hcpVault = ctx.getBean(HcpVault.class);
            String jwk = hcpVault.getSecret();
            RSAKey rsaKey = RSAKey.parse(jwk);
            JWKSet jwkSet = new JWKSet(rsaKey);
            return new ImmutableJWKSet<>(jwkSet);
        }
        catch (NoSuchBeanDefinitionException e){
            RSAKey rsaKey = new RSAKeyGenerator(2048).generate();
            JWKSet jwkSet = new JWKSet(rsaKey);
            return new ImmutableJWKSet<>(jwkSet);
        }

    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer(AccountRepository accountRepository) {
        return context -> {
            if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
                String uuid = context.getPrincipal().getName();
                context.getClaims().claims(claims -> {
                    Account account = accountRepository.findById(UUID.fromString(uuid)).orElseThrow();
                    Set<String> roles = AuthorityUtils.authorityListToSet(context.getPrincipal().getAuthorities())
                            .stream()
                            .map(c -> c.replaceFirst("^ROLE_", ""))
                            .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
                    claims.put("roles", roles);
                    claims.put("X-Account-Uuid", uuid);
                    claims.put("username", account.getUsername());
                    claims.put("email", account.getEmail());
                });
            }
        };
    }
}
