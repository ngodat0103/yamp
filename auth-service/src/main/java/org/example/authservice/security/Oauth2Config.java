package org.example.authservice.security;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.example.authservice.service.impl.RedisOauth2AuthorizationService;
import org.example.authservice.vault.HcpVault;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class Oauth2Config {
    @Bean
    RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate){
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }


    @Bean
    RedisOauth2AuthorizationService authorizationService(RedisOperations<String,String> redisOperations,
                                                         RegisteredClientRepository registeredClientRepository,
                                                         AutowireCapableBeanFactory autowireCapableBeanFactory){
        return new RedisOauth2AuthorizationService(redisOperations,registeredClientRepository,autowireCapableBeanFactory);
    }


    @Bean HcpVault hcpVault() throws IOException, com.nimbusds.oauth2.sdk.ParseException {
        return new HcpVault();
    }

    @Bean
    JWKSource<SecurityContext> jwkSource(HcpVault hcpVault) throws ParseException {
        String jwk = hcpVault.getSecret();
        RSAKey rsaKey = RSAKey.parse(jwk);
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

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
                    claims.put("x-account-uuid", uuid);
                });
            }
        };
    }
}
