package com.tenpo.challenge.services;

import com.tenpo.challenge.model.CalculateRequest;
import com.tenpo.challenge.repository.CalculateRequestRepository;
import org.jboss.logging.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CalculateRequestService {

    private static final Logger logger = Logger.getLogger(CalculateRequestService.class);

    private final CalculateRequestRepository calculateRequestRepository;

    public CalculateRequestService(CalculateRequestRepository calculateRequestRepository) {
        this.calculateRequestRepository = calculateRequestRepository;
    }

    /**
     * Create {@link CalculateRequest}
     * @param valueA
     * @param valueB
     * @param result
     * @return
     */
    @Async
    public CalculateRequest create(Long valueA, Long valueB, Long result){
        CalculateRequest calculateRequest = new CalculateRequest(valueA,valueB,result, LocalDateTime.now());
        logger.info("CalculateRequestService.create() params: ".concat(calculateRequest.toString()));
        return this.calculateRequestRepository.save(calculateRequest);
     }

    /**
     * Get all {@link CalculateRequest} paginated
     * @return
     */
    public Page<CalculateRequest> findAll(Pageable pageable){
        logger.info("CalculateRequestService.findAll()");
        return calculateRequestRepository.findAll(pageable);
    }


}
