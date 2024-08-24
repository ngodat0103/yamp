package com.github.ngodat0103.yamp.authsvc.session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration(proxyBeanMethods = false)
@EnableRedisHttpSession
public class SessionConfiguration {

    @Primary
    @Bean
    DefaultCookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("AUTHSESSIONID");
        serializer.setDomainName("localhost");
        serializer.setCookiePath("/");
        serializer.setUseBase64Encoding(false);
        return serializer;
    }


}
