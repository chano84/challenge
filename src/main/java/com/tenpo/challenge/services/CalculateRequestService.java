package com.tenpo.challenge.services;

import com.tenpo.challenge.model.CalculateRequest;
import com.tenpo.challenge.repository.CalculateRequestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CalculateRequestService {

    private final CalculateRequestRepository calculateRequestRepository;

    public CalculateRequestService(CalculateRequestRepository calculateRequestRepository) {
        this.calculateRequestRepository = calculateRequestRepository;
    }

    @Async
    public CalculateRequest create(Long valueA, Long valueB, Long result){
        CalculateRequest calculateRequest = new CalculateRequest(valueA,valueB,result, LocalDateTime.now());
        this.calculateRequestRepository.save(calculateRequest);
        return calculateRequest;
    }

    /**
     * Realizar las consultas paginadas
     * @return
     */
    public Page<CalculateRequest> findAll(Pageable pageable){
        return calculateRequestRepository.findAll(pageable);
    }


}
