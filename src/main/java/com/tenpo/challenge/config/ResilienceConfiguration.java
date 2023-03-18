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
     * Límite de 10 solicitudes por segundo
     */
    @Value("${resilience.limit_for_period:1}")
    private int limitPorPeriod;

    /**
     * Ventana de tiempo de 1 segundo
     */
    @Value("${resilience.limit_refresh_period:1}")
    private int limitRefreshPeriod;

    /**
     *Timeout máximo de 100 ms para adquirir un permiso
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
}
