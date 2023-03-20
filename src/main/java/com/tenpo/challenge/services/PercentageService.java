package com.tenpo.challenge.services;

import com.tenpo.challenge.exceptions.BusinessException;
import com.tenpo.challenge.external.PercentageClient;
import com.tenpo.challenge.redis.RedisClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        return this.getValue().orElse(this.getLastValue());
    }

    /**
     * Busca en cache o servicio externo
     * @return Long
     */
    private Optional<Long> getValue(){
        Long value = this.redisClient.getNumber().orElse(this.getValueOfClient());
        return Optional.ofNullable(value);
    }

    /**
     *
     * @return
     */
    private Long getValueOfClient() {
        try {
            Long value = this.percentageClient.getPercentage().getValue();
            this.redisClient.setNumber(value);
            this.redisClient.setLastNumber(value);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Busca el ultimo valor retornado
     * @return Long
     */
    private Long getLastValue(){
        return this.redisClient.getLastNumber()
                .orElseThrow( () -> new BusinessException("The value doesn't exist"));
    }

}
