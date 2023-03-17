package com.tenpo.challenge.external;

import com.tenpo.challenge.external.dto.PercentageDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

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

    public PercentageDTO getPercentage() {
        return webClient.get()
                .uri("")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(PercentageDTO.class)
                .block();
    }

}

