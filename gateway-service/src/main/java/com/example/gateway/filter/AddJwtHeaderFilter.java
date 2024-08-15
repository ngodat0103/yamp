package com.example.gateway.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.server.authorization.AuthorizationWebFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
public class AddJwtHeaderFilter implements WebFilter {

    private final ReactiveOAuth2AuthorizedClientService reactiveOAuth2AuthorizedClientService;
    private static final Log logger = LogFactory.getLog(AuthorizationWebFilter.class);
    public AddJwtHeaderFilter(ReactiveOAuth2AuthorizedClientService reactiveOAuth2AuthorizedClientService) {
        this.reactiveOAuth2AuthorizedClientService = reactiveOAuth2AuthorizedClientService;
    }


    @Override
    @NonNull
    public Mono<Void> filter(@NonNull ServerWebExchange exchange, WebFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .filter((c) -> c.getAuthentication() != null)
                .map(SecurityContext::getAuthentication)
                .filter((a) -> a instanceof OAuth2AuthenticationToken)
                .switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
                .cast(OAuth2AuthenticationToken.class)
                .flatMap(token -> {
                    String clientId = token.getAuthorizedClientRegistrationId();
                    String principalName = token.getName();
                    return reactiveOAuth2AuthorizedClientService.loadAuthorizedClient(clientId, principalName)
                            .map(OAuth2AuthorizedClient::getAccessToken)
                            .flatMap((accessToken) -> {
                                exchange.getRequest().mutate().headers(
                                        headers -> headers.setBearerAuth(accessToken.getTokenValue())
                                );
                                return chain.filter(exchange);
                            });
                });
    }

}
