package com.tenpo.challenge.services;

import com.tenpo.challenge.external.PercentageClient;
import com.tenpo.challenge.redis.RedisClient;
import org.springframework.stereotype.Service;

@Service
public class PercentageService {

    private final PercentageClient percentageClient;

    private final RedisClient redisClient;

    public PercentageService(PercentageClient percentageClient, RedisClient redisClient) {
        this.percentageClient = percentageClient;
        this.redisClient = redisClient;
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
        Long value = this.redisClient.getNumber();
        if ( value == null ) {
            try {
                value = this.percentageClient.getPercentage().getValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if ( value != null ){
                this.redisClient.setNumber(value);
                this.redisClient.setLastNumber(value);
            }
        }
        return value;
    }

    /**
     * Busca el ultimo valor retornado
     * @return Long
     */
    private Long getLastValue(){
        Long value =  this.redisClient.getLastNumber();
        if ( value == null ){
            throw new RuntimeException("The value doesn't exist");
        }
        return value;
    }

}
