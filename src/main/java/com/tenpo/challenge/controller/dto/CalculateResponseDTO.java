package com.tenpo.challenge.controller.dto;

import java.math.BigDecimal;

public class CalculateResponseDTO {

    private BigDecimal value;

    public CalculateResponseDTO(BigDecimal value){
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
