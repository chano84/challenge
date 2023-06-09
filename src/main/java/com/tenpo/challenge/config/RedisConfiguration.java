package com.tenpo.challenge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class RedisConfiguration {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private Integer port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(this.host);
        config.setPort(this.port);
        return new LettuceConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Long> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Long> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Long.class));
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Long.class));
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
