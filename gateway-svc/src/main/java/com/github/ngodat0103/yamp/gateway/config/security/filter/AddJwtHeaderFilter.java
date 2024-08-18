package com.github.ngodat0103.yamp.gateway.config.security.filter;

import lombok.AllArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.server.authorization.AuthorizationWebFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;



@AllArgsConstructor
public class AddJwtHeaderFilter implements WebFilter {
    private final ReactiveOAuth2AuthorizedClientService reactiveOAuth2AuthorizedClientService;
    private static final Log logger = LogFactory.getLog(AuthorizationWebFilter.class);

    @Override
    @NonNull
    public Mono<Void> filter(@NonNull ServerWebExchange exchange, WebFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .filter(ctx ->ctx.getAuthentication()!=null && ctx.getAuthentication() instanceof OAuth2AuthenticationToken)
                .switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
                .map(SecurityContext::getAuthentication)
                .cast(OAuth2AuthenticationToken.class)
                .flatMap(token -> {
                    String principal = token.getName();
                    String registrationId = token.getAuthorizedClientRegistrationId();
                    return reactiveOAuth2AuthorizedClientService.loadAuthorizedClient(registrationId,principal)
                            .flatMap(oauth2-> {
                                ServerHttpRequest request = exchange.getRequest();
                                request.mutate().headers(h->h.setBearerAuth(oauth2.getAccessToken().getTokenValue()));
                                return chain.filter(exchange);
                            });
                });



    }

}
