package com.tenpo.challenge.services;

import com.tenpo.challenge.repository.CalculateRequestRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CalculateService {

    private final CalculateRequestRepository calculateRequestRepository;

    public CalculateService(CalculateRequestRepository calculateRequestRepository) {
        this.calculateRequestRepository = calculateRequestRepository;
    }

    public BigDecimal calculate(Long valueA, Long valueB ){
        return null;
    }

}
