package com.github.ngodat0103.yamp.authsvc.session;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.commons.util.InetUtilsProperties;
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
    DefaultCookieSerializer cookieSerializer(InetUtilsProperties inetUtilsProperties, ServerProperties serverProperties) {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("AUTHSESSIONID");
        serializer.setDomainName(inetUtilsProperties.getDefaultHostname());
        serializer.setCookiePath(serverProperties.getServlet().getContextPath());
        serializer.setUseBase64Encoding(false);
        return serializer;
    }


}
