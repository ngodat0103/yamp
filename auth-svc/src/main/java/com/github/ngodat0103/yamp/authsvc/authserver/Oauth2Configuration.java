package com.github.ngodat0103.yamp.authsvc.authserver;

import com.github.ngodat0103.yamp.authsvc.persistence.entity.Account;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.AccountRepository;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
@Setter
public class Oauth2Configuration {


    // Create a JWKSource bean that reads the JWK from the k8s secret,
    // If not present, the JwkSource random key will be used base on spring autoconfiguration
    @Bean
    @ConditionalOnProperty(prefix = "k8s.secret", name = "jwk")
    JWKSource<SecurityContext> jwkSourceFromK8sSecret(@Value("${k8s.secret.jwk}") String jwk) throws ParseException {
        Assert.notNull(jwk, "jwk must not be null");
        JWKSet jwkSet = new JWKSet(RSAKey.parse(jwk));
        return new ImmutableJWKSet<>(jwkSet);
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
