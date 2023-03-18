package com.tenpo.challenge.controller;

import com.tenpo.challenge.model.CalculateRequest;
import com.tenpo.challenge.services.CalculateRequestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/calculate-request")
public class CalculateRequestController {

    private final CalculateRequestService calculateRequestService;

    public CalculateRequestController(CalculateRequestService calculateRequestService) {
        this.calculateRequestService = calculateRequestService;
    }

    @GetMapping
    public Page<CalculateRequest> getUsers(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CalculateRequest> pages = this.calculateRequestService.findAll(pageable);
        return pages;

    }


}
