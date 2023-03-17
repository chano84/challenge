package com.tenpo.challenge.services;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CalculateService {

    private final CalculateRequestService calculateRequestService;

    private final PercentageService percentageService;

    public CalculateService(CalculateRequestService calculateRequestService, PercentageService percentageService) {
        this.calculateRequestService = calculateRequestService;
        this.percentageService = percentageService;
    }

    public BigDecimal calculate(Long valueA, Long valueB ){
        Long percentage = this.percentageService.getPercentage();
        Long result = valueA * valueB * percentage;
        this.calculateRequestService.create(valueA,valueB,result);
        return BigDecimal.valueOf(result);
    }

}
