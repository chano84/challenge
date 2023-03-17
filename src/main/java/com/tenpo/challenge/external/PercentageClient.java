package com.tenpo.challenge.external;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class PercentageClient {

    private final String url;

    private final WebClient webClient;

    public PercentageClient(@Value("${tenpo.percentage.url}") String url, WebClient webClient) {
        this.url = url;
        this.webClient = webClient;
    }

    public Long getPercentage() {
        return webClient.get()
                .uri(this.url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Long.class)
                .block();
    }

}

