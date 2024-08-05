package org.example.authservice.authserver;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.example.authservice.vault.HcpVault;
import org.example.authservice.vault.HcpVaultConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.text.ParseException;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class Oauth2Configuration {
    @Bean
    @ConditionalOnBean(HcpVaultConfiguration.class)
    JWKSource<SecurityContext> jwkSourceFromHcpVault(HcpVault hcpVault) throws ParseException {
        String jwk = hcpVault.getSecret();
        RSAKey rsaKey = RSAKey.parse(jwk);
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

//    @Bean
//    @ConditionalOnMissingBean(HcpVaultConfiguration.class)
//    JWKSource<SecurityContext> jwkSource() throws JOSEException {
//        RSAKey rsaKey = new RSAKeyGenerator(2048).generate();
//        JWKSet jwkSet = new JWKSet(rsaKey);
//        return new ImmutableJWKSet<>(jwkSet);
//    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() {
        return context -> {
            if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
                String uuid = context.getPrincipal().getName();
                context.getClaims().claims(claims -> {
                    Set<String> roles = AuthorityUtils.authorityListToSet(context.getPrincipal().getAuthorities())
                            .stream()
                            .map(c -> c.replaceFirst("^ROLE_", ""))
                            .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
                    claims.put("roles", roles);
                    claims.put("X-Account-Uuid", uuid);
                });
            }
        };
    }
}
