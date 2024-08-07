package com.example.userservice.session;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.session.DefaultCookieSerializerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.DefaultCookieSerializer;

@EnableRedisHttpSession
@Configuration(proxyBeanMethods = false)
public class SessionConfiguration {


    @Bean
    @Primary
    DefaultCookieSerializer defaultCookieSerializer(){
        DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();
        defaultCookieSerializer.setCookieName("JSESSIONID");
        defaultCookieSerializer.setDomainName("localhost");
        defaultCookieSerializer.setUseBase64Encoding(false);
        defaultCookieSerializer.setUseHttpOnlyCookie(true);
        return defaultCookieSerializer;
    }
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }
}
