package org.example.authservice.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizationSuccessHandler;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {



    @Bean
    SecurityFilterChain oauth2FilterChain(HttpSecurity http) throws Exception {

        // for testing purposes
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults());
        http.exceptionHandling(e -> e.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")));
      OAuth2AuthorizationServerConfigurer oAuth2AuthorizationServerConfigurer =  http.getConfigurer(OAuth2AuthorizationServerConfigurer.class);
      oAuth2AuthorizationServerConfigurer.tokenRevocationEndpoint( e ->
              e.revocationResponseHandler( (request, response, authentication) -> {
                          SecurityContext securityContext = SecurityContextHolder.getContext();
                          if(securityContext != null){
                              SecurityContextHolder.clearContext();
                          }
                  request.getSession().invalidate();
                  response.setStatus(200);
                  response.getWriter().write("Token revoked");
              }
              )
      );

        http.rememberMe(AbstractHttpConfigurer::disable);
        return http.build();
    }
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests( authorize -> authorize.
                                anyRequest().permitAll()
                        );

                http.formLogin(Customizer.withDefaults());
       return http.build();
    }

    @Bean
    AuthorizationServerSettings authorizationServerSettings(){
        return AuthorizationServerSettings.builder().build();
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
