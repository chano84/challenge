package com.tenpo.challenge.external;

import com.tenpo.challenge.external.dto.PercentageDTO;
import io.github.resilience4j.retry.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Component
public class PercentageClient {

    private final String url;

    private final Retry retry;

    private final WebClient webClient;

    public PercentageClient(@Value("${tenpo.percentage.url}") String url, Retry retry) {
        this.url = url;
        this.webClient =  WebClient.builder()
                .baseUrl(url)
                .build();
        this.retry = retry;
    }

    public PercentageDTO getPercentage() throws Exception {
        PercentageDTO response = Retry.decorateCallable(this.retry, () -> this.callService()).call();
        return response;
    }

    private PercentageDTO callService() {
        return webClient.get()
                .uri("")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(PercentageDTO.class)
                .block();
    }

}

