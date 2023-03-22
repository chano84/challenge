package com.tenpo.challenge.controller.dto;


import javax.validation.constraints.NotNull;

public class CalculateDTO {

    @NotNull(message = "Value not null")
    private Long valueA;

    @NotNull(message = "Value not null")
    private Long valueB;

    public CalculateDTO(){}

    public CalculateDTO(Long valueA, Long valueB){
        this.valueA = valueA;
        this.valueB = valueB;
    }


    public Long getValueA() {
        return valueA;
    }



    public void setValueA(
             Long valueA) {
        this.valueA = valueA;
    }

    public Long getValueB() {
        return valueB;
    }


    public void setValueB(
             Long valueB) {
        this.valueB = valueB;
    }

    @Override
    public String toString() {
        return "CalculateDTO{" +
                "valueA=" + valueA +
                ", valueB=" + valueB +
                '}';
    }
}
