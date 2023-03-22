package com.tenpo.challenge.controller;

import com.tenpo.challenge.controller.dto.CalculateDTO;
import com.tenpo.challenge.controller.dto.CalculateResultDTO;
import com.tenpo.challenge.services.CalculateService;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;


import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@Validated
public class CalculateController {

    private static final Logger logger = Logger.getLogger(CalculateController.class);

    private final CalculateService calculateService;

    private final RateLimiterRegistry rateLimiterRegistry;

    public CalculateController(CalculateService calculateService, RateLimiterRegistry rateLimiterRegistry) {
        this.calculateService = calculateService;
        this.rateLimiterRegistry = rateLimiterRegistry;
    }

    @PostMapping(path = "/calculate")
    public ResponseEntity<CalculateResultDTO> calculate(@Valid @RequestBody CalculateDTO requestCalculateDTO) {
        RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter("myRateLimiter");
        if (rateLimiter.acquirePermission()) {
            logger.info("CalculateController.calculate() params: ".concat(requestCalculateDTO.toString()));
            BigDecimal result = this.calculateService.calculate(requestCalculateDTO.getValueA(), requestCalculateDTO.getValueB());
            return new ResponseEntity<>(new CalculateResultDTO(result),HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }


    }

}
