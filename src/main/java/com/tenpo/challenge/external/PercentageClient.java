package com.tenpo.challenge.external;

import com.tenpo.challenge.controller.CalculateRequestController;
import com.tenpo.challenge.exceptions.BusinessException;
import com.tenpo.challenge.external.dto.PercentageDTO;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Component
public class PercentageClient {

    private final String url;

    private final WebClient webClient;

    private static final Logger logger = Logger.getLogger(PercentageClient.class);

    public PercentageClient(@Value("${tenpo.percentage.url}") String url) {
        this.url = url;
        this.webClient =  WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public PercentageDTO getPercentage() throws BusinessException {
        logger.info("PercentageClient.getPercentage()");
        PercentageDTO percentageDTOMono = webClient.get()
                .uri("")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(PercentageDTO.class)
                .onErrorResume(Mono::error)
                .retryWhen(Retry.fixedDelay(3, Duration.of(30, ChronoUnit.MILLIS))
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                                new BusinessException("Service Percentage not found")))
                .block();
        logger.info("PercentageClient.getPercentage().end");
        return percentageDTOMono;
    }

}

