package com.github.ngodat0103.yamp.gateway.security;

import com.github.ngodat0103.yamp.gateway.security.filter.AddJwtHeaderFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.reactive.config.EnableWebFlux;

@Configuration
@EnableWebFlux
@EnableWebFluxSecurity
public class SecurityConfiguration {

  @Bean
  SecurityWebFilterChain actuatorFilterChain(ServerHttpSecurity http) {
    http.securityMatcher(new PathPatternParserServerWebExchangeMatcher("/actuator/**"));
    http.authorizeExchange(
        exchange ->
            exchange
                .pathMatchers("/actuator/health/liveness", "/actuator/health/readiness")
                .permitAll()
                .pathMatchers("/actuator/prometheus")
                .permitAll()
                .anyExchange()
                .hasRole("ACTUATOR_ADMIN"));
    return http.build();
  }

  @Bean
  SecurityWebFilterChain docsFilterChain(ServerHttpSecurity http) {
    OrServerWebExchangeMatcher orServerWebExchangeMatcher =
        new OrServerWebExchangeMatcher(
            new PathPatternParserServerWebExchangeMatcher("/ui-docs/**"),
            new PathPatternParserServerWebExchangeMatcher("/api-docs/**"));
    http.securityMatcher(orServerWebExchangeMatcher);
    http.authorizeExchange(exchange -> exchange.anyExchange().permitAll());
    return http.build();
  }

  @Bean
  SecurityWebFilterChain grafanaFilterChain(ServerHttpSecurity http) {
    http.cors(ServerHttpSecurity.CorsSpec::disable);
    http.csrf(ServerHttpSecurity.CsrfSpec::disable);
    http.securityMatcher(new PathPatternParserServerWebExchangeMatcher("/grafana/**"));
    http.authorizeExchange(exchange -> exchange.anyExchange().permitAll());
    return http.build();
  }

  @Bean
  SecurityWebFilterChain apiFilterChain(
      ServerHttpSecurity http,
      ReactiveOAuth2AuthorizedClientService reactiveOAuth2AuthorizedClientService) {
    http.csrf(ServerHttpSecurity.CsrfSpec::disable);
    ServerWebExchangeMatcher apiMatcher =
        new PathPatternParserServerWebExchangeMatcher("/api/v1/**");
    http.securityMatcher(apiMatcher);
    http.authorizeExchange(
        exchange ->
            exchange
                .pathMatchers("/favicon.ico")
                .permitAll()
                .pathMatchers("/api/v1/auth/oauth2/authorize")
                .permitAll()
                .pathMatchers("/api/v1/auth/login", "/api/v1/auth/account/logout")
                .permitAll()
                .pathMatchers("/api/v1/auth/oauth2/token")
                .permitAll()
                //
                // .pathMatchers("/login/oauth2/code/gateway-service").permitAll()
                .pathMatchers("/api/v1/user/get-me")
                .authenticated()
                .pathMatchers("/api/v1/user/test-endpoint")
                .authenticated()
                .pathMatchers("/api/v1/auth/actuator/**")
                .denyAll()
                .pathMatchers("/api/v1/auth/accounts/roles")
                .authenticated()
                .pathMatchers(HttpMethod.POST, "/api/v1/auth/oauth2/authorize")
                .permitAll()
                .pathMatchers(HttpMethod.POST, "/api/v1/auth/oauth2/token")
                .permitAll()
                .pathMatchers(HttpMethod.GET, "/api/v1/products/**")
                .permitAll()
                .anyExchange()
                .permitAll());
    //        http.oauth2Client(Customizer.withDefaults());
    //        http.oauth2Login(Customizer.withDefaults());
    //        http.logout(logout -> logout.logoutUrl("/api/v1/auth/logout"));
    http.oauth2ResourceServer(resource -> resource.jwt(Customizer.withDefaults()));

    http.addFilterAfter(
        new AddJwtHeaderFilter(reactiveOAuth2AuthorizedClientService),
        SecurityWebFiltersOrder.AUTHORIZATION);
    WebSessionServerSecurityContextRepository webSessionServerSecurityContextRepository =
        new WebSessionServerSecurityContextRepository();
    webSessionServerSecurityContextRepository.setCacheSecurityContext(false);
    http.securityContextRepository(webSessionServerSecurityContextRepository);
    return http.build();
  }
}
