package com.tenpo.challenge.config;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.time.Duration;

@Configuration
public class ResilienceConfiguration {

    /**
     * Limite de solicitudes
     */
    @Value("${resilience.limit_for_period:3}")
    private int limitPorPeriod;

    /**
     * Ventana de tiempo por segundo
     */
    @Value("${resilience.limit_refresh_period:1}")
    private int limitRefreshPeriod;

    /**
     *Timeout maximo para adquirir un request
     */
    @Value("${resilience.limit_duration:1}")
    private int timeoutDuration;

    @Bean
    public RateLimiterRegistry rateLimiterRegistry() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(this.limitPorPeriod)
                .limitRefreshPeriod(Duration.ofMinutes(this.limitRefreshPeriod))
                .timeoutDuration(Duration.ofSeconds(this.timeoutDuration))
                .build();
        RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.of(config);
        return rateLimiterRegistry;
    }




}
