package com.tenpo.challenge.controller;

import com.tenpo.challenge.controller.dto.CalculateDTO;
import com.tenpo.challenge.controller.dto.CalculateResultDTO;
import com.tenpo.challenge.services.CalculateService;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/calculate")
public class CalculateController {

    private final CalculateService calculateService;

    public CalculateController(CalculateService calculateService) {
        this.calculateService = calculateService;
    }

    @PostMapping
    @RateLimiter(name = "calculate", fallbackMethod = "calculateFallback")
    public ResponseEntity<CalculateResultDTO> calculate(@RequestBody CalculateDTO requestCalculateDTO) {
         BigDecimal result = this.calculateService.calculate(requestCalculateDTO.getValueA(), requestCalculateDTO.getValueB());
         return new ResponseEntity<>(new CalculateResultDTO(result),HttpStatus.OK);
    }

    public ResponseEntity<String> calculateFallback(Throwable ex) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Endpoint calculate está ocupado. Por favor, inténtalo más tarde.");
    }

}
