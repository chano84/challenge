package com.tenpo.challenge.external;

import com.tenpo.challenge.exceptions.BusinessException;
import com.tenpo.challenge.external.dto.PercentageDTO;

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

    public PercentageClient(@Value("${tenpo.percentage.url}") String url) {
        this.url = url;
        this.webClient =  WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public PercentageDTO getPercentage() throws BusinessException {
        PercentageDTO percentageDTOMono = webClient.get()
                .uri("")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(PercentageDTO.class)
                .onErrorResume(Mono::error)
                .retryWhen(Retry.fixedDelay(3, Duration.of(1, ChronoUnit.SECONDS))
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                                new BusinessException("Service Percentage not found")))
                .block();
        return percentageDTOMono;
    }

}

