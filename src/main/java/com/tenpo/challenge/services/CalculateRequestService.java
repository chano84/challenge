package com.tenpo.challenge.services;

import com.tenpo.challenge.model.CalculateRequest;
import com.tenpo.challenge.model.CalculateRequestPaginated;
import com.tenpo.challenge.repository.CalculateRequestRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class CalculateRequestService {

    private final CalculateRequestRepository calculateRequestRepository;

    public CalculateRequestService(CalculateRequestRepository calculateRequestRepository) {
        this.calculateRequestRepository = calculateRequestRepository;
    }

    @Async
    public CalculateRequest create(Long result){
        CalculateRequest calculateRequest = new CalculateRequest();
        this.calculateRequestRepository.save(calculateRequest);
        return calculateRequest;
    }

    /**
     * Realizar las consultas paginadas
     * @return
     */
    public CalculateRequestPaginated findPaginated(){
        return new CalculateRequestPaginated();
    }


}