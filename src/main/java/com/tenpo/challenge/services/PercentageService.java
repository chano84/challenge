package com.tenpo.challenge.services;

import com.tenpo.challenge.external.PercentageClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class PercentageService {

    private final PercentageClient percentageClient;

    private final RedisTemplate<String,Long> redisTemplate;

    private static final String LAST_NUMBER_KEY = "lastNumber";

    private static final String NUMBER_KEY = "numbero";

    public PercentageService(PercentageClient percentageClient, RedisTemplate<String, Long> redisTemplate) {
        this.percentageClient = percentageClient;
        this.redisTemplate = redisTemplate;
    }

    /**
     * Si esta en redis, retorno redis sino voy al servicio, si el servicio faya retorno el ultimo valor utilizable
     * @return Long
     */
    public Long getPercentage(){
        Long value = this.getValue();
        if ( value == null){
            value = this.getLastValue();
        }
        return value;
    }

    /**
     * Busca en cache o servicio externo
     * @return Long
     */
    private Long getValue(){
        Long value = this.redisTemplate.opsForValue().get(NUMBER_KEY);
        if ( value == null ) {
            value = this.percentageClient.getPercentage().getValue();
            if ( value != null ){
                this.redisTemplate.opsForValue().set(NUMBER_KEY,value);
                this.redisTemplate.opsForValue().set(LAST_NUMBER_KEY,value);
            }
        }
        return value;
    }

    /**
     * Busca el ultimo valor retornado
     * @return Long
     */
    private Long getLastValue(){
        Long value =  this.redisTemplate.opsForValue().get(LAST_NUMBER_KEY);
        if ( value == null ){
            throw new RuntimeException("The value doesn't exist");
        }
        return value;
    }

}
