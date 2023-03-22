package com.tenpo.challenge.services;

import com.tenpo.challenge.exceptions.BusinessException;
import com.tenpo.challenge.external.PercentageClient;
import com.tenpo.challenge.redis.RedisClient;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PercentageService {

    private static final Logger logger = Logger.getLogger(PercentageService.class);

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
        logger.info("PercentageService.getPercentage()");
        return this.getValue().orElseGet( ()-> this.getLastValue());
    }

    /**
     * Busca en cache o servicio externo
     * @return Long
     */
    private Optional<Long> getValue(){
        logger.info("PercentageService.getValue()");
        Long value = this.redisClient.getNumber().orElseGet( () -> this.getValueOfClient());
        logger.info("PercentageService.getValue().end");
        return Optional.ofNullable(value);
    }

    /**
     *
     * @return get Value of client if doesnt exist return null
     */
    private Long getValueOfClient() {
        logger.info("PercentageService.getValueOfClient()");
        Long value = null;
        try {
            value = this.percentageClient.getPercentage().getValue();
            this.redisClient.setNumber(value);
            this.redisClient.setLastNumber(value);
            logger.info("PercentageService.getValueOfClient() end value of client: ".concat(value.toString()));
            return value;
        } catch (BusinessException e) {
            logger.info("PercentageService.getValueOfClient() No found value");
            return value;
        }
    }

    /**
     * Busca el ultimo valor retornado
     * @return Long
     */
    private Long getLastValue(){
        logger.info("PercentageService.gertLastValue()");
        return this.redisClient.getLastNumber()
                .orElseThrow( () -> new BusinessException("The Last Value doesn't exist"));
    }

}
