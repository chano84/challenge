package com.tenpo.challenge.external;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class PercentageClient {

    private final String url;

    public PercentageClient(@Value("${tenpo.percentage.url}") String url) {
        this.url = url;
    }

    /**
     * TODO agregar client para obtener el valor mockeado
     * @return Long
     */
    public Long getPercentage(){
        return Long.valueOf(3);
    }
}
