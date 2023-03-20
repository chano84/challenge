package com.tenpo.challenge.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisClient {

    private final RedisTemplate<String,Long> redisTemplate;

    private static final String LAST_NUMBER_KEY = "rop";

    private static final String NUMBER_KEY = "number";

    private final Duration duration;


    public RedisClient(RedisTemplate<String, Long> redisTemplate, @Value("${spring.data.redis.key.ttl}") Long ttl) {
        this.redisTemplate = redisTemplate;
        this.duration = Duration.ofSeconds(ttl);
    }

    public void setNumber(Long value){
        this.redisTemplate.opsForValue().set(NUMBER_KEY, value);
        this.redisTemplate.expire(NUMBER_KEY, this.duration);
    }

    public Long getNumber(){
        return this.redisTemplate.opsForValue().get(NUMBER_KEY);
    }

    public void setLastNumber(Long value){
        this.redisTemplate.opsForValue().set(LAST_NUMBER_KEY, value);
    }

    public Long getLastNumber(){
        return this.redisTemplate.opsForValue().get(LAST_NUMBER_KEY);
    }

}
