package com.tenpo.challenge.external.dto;

public class PercentageDTO {

    private Long value;

    public PercentageDTO(){}

    public PercentageDTO(Long value){
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
