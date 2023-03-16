package com.tenpo.challenge.repository;

import com.tenpo.challenge.model.CalculateRequest;
import org.springframework.stereotype.Repository;

@Repository
public class CalculateRequestRepository {

    public CalculateRequest save(CalculateRequest calculateRequest){
        return calculateRequest;
    }

}
