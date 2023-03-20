package com.tenpo.challenge.services;

import com.tenpo.challenge.controller.CalculateController;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CalculateService {

    private static final Logger logger = Logger.getLogger(CalculateService.class);

    private final CalculateRequestService calculateRequestService;

    private final PercentageService percentageService;

    public CalculateService(CalculateRequestService calculateRequestService, PercentageService percentageService) {
        this.calculateRequestService = calculateRequestService;
        this.percentageService = percentageService;
    }

    public BigDecimal calculate(Long valueA, Long valueB ){
        logger.info("CalculateService.calculate()");
        Long percentage = this.percentageService.getPercentage();
        Long result = valueA * valueB * percentage;
        this.calculateRequestService.create(valueA,valueB,result);
        logger.info("CalculateService.calculate().end ".concat(result.toString()));
        return BigDecimal.valueOf(result);
    }

}
