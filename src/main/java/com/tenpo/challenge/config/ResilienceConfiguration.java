package com.tenpo.challenge.config;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.time.Duration;

@Configuration
public class ResilienceConfiguration {

    /**
     * Límite de solicitudes por segundo
     */
    @Value("${resilience.limit_for_period:1}")
    private int limitPorPeriod;

    /**
     * Ventana de tiempo por segundo
     */
    @Value("${resilience.limit_refresh_period:1}")
    private int limitRefreshPeriod;

    /**
     *Timeout máximo para adquirir un request
     */
    @Value("${resilience.limit_duration:100}")
    private int timeoutDuration;

    @Bean
    public RateLimiterRegistry rateLimiterRegistry() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(this.limitPorPeriod)
                .limitRefreshPeriod(Duration.ofSeconds(this.limitRefreshPeriod))
                .timeoutDuration(Duration.ofMillis(this.timeoutDuration))
                .build();
        RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.of(config);
        return rateLimiterRegistry;
    }

    @Bean
    public RateLimiter rateLimiter(RateLimiterRegistry rateLimiterRegistry) {
        return rateLimiterRegistry.rateLimiter("calculate");
    }

    @Bean
    public Retry retry(){
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(500))
                .build();
        Retry retry = io.github.resilience4j.retry.Retry.of("retry", config);
        return retry;
    }

}
