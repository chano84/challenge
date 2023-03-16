package com.tenpo.challenge.services;

import com.tenpo.challenge.repository.CalculateRequestRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CalculateService {

    private final CalculateRequestRepository calculateRequestRepository;

    private final PercentageService percentageService;

    public CalculateService(CalculateRequestRepository calculateRequestRepository, PercentageService percentageService) {
        this.calculateRequestRepository = calculateRequestRepository;
        this.percentageService = percentageService;
    }

    public BigDecimal calculate(Long valueA, Long valueB ){
        Long percentage = this.percentageService.getPercentage();
        Long result = valueA * valueB * percentage;
        return BigDecimal.valueOf(result);
    }

}
