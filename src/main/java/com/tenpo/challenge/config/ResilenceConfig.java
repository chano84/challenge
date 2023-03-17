package com.tenpo.challenge.config;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ResilenceConfig {

    @Bean
    public RateLimiterRegistry rateLimiterRegistry() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(1) // Límite de 10 solicitudes por segundo
                .limitRefreshPeriod(Duration.ofSeconds(1)) // Ventana de tiempo de 1 segundo
                .timeoutDuration(Duration.ofMillis(100)) // Timeout máximo de 100 ms para adquirir un permiso
                .build();
        RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.of(config);
        return rateLimiterRegistry;
    }

    @Bean
    public RateLimiter rateLimiter(RateLimiterRegistry rateLimiterRegistry) {
        return rateLimiterRegistry.rateLimiter("calculate");
    }
}
