package com.tenpo.challenge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.KeyspaceConfiguration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;


@Configuration
@EnableRedisRepositories(keyspaceConfiguration = RedisConfiguration.RedisKeyspaceConfiguration.class)
public class RedisConfiguration {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
         return new LettuceConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Long> redisTemplate() {
        RedisTemplate<String, Long> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }

    public static class RedisKeyspaceConfiguration extends KeyspaceConfiguration {

        @Value("${spring.data.redis.key.ttl}")
        private Long key;

        @Value("${redis.keyspace:percent}")
        private String keyspace;

        @Override
        public boolean hasSettingsFor(Class<?> type) {
            return true;
        }

        @Override
        public KeyspaceSettings getKeyspaceSettings(Class<?> type) {
            if (type.getSimpleName().equals(this.keyspace)) {
                KeyspaceSettings keyspaceSettings = new KeyspaceSettings(type, this.keyspace);
                keyspaceSettings.setTimeToLive(key);
                return keyspaceSettings;
            }
            return new KeyspaceSettings(type, type.getSimpleName());
        }
    }

}
