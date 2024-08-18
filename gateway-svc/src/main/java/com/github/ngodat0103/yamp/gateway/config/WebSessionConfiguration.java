package com.github.ngodat0103.yamp.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;

@Configuration
@EnableRedisWebSession
public class WebSessionConfiguration {
}
