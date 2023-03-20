package com.tenpo.challenge.controller.dto;

import javax.validation.constraints.NotNull;

public record CalculateDTO (
    @NotNull(message = "valueA cannot be not null")
    Long valueA,
    @NotNull(message = "valueB cannot be not null")
    Long valueB
){}
