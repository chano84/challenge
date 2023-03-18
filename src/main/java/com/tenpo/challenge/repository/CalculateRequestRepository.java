package com.tenpo.challenge.repository;

import com.tenpo.challenge.model.CalculateRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculateRequestRepository extends JpaRepository<CalculateRequest, Long> {

    Page<CalculateRequest> findAll(Pageable pageable);

}
