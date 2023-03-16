package com.tenpo.challenge.controller.dto;

import java.math.BigDecimal;

public class ResultCalculateDTO {

    private BigDecimal value;

    public ResultCalculateDTO(BigDecimal value){
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
