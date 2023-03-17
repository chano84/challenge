package com.tenpo.challenge.controller.dto;

import java.math.BigDecimal;

public class ResponseCalculateDTO {

    private BigDecimal value;

    public ResponseCalculateDTO(BigDecimal value){
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
