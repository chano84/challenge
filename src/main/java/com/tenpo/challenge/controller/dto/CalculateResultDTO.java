package com.tenpo.challenge.controller.dto;

import java.math.BigDecimal;

public class CalculateResultDTO {

    private BigDecimal value;

    public CalculateResultDTO(BigDecimal value){
        this.value = value;
    }

    public CalculateResultDTO(){}

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
