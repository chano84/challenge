package com.tenpo.challenge.controller;

import com.tenpo.challenge.controller.dto.CalculateDTO;
import com.tenpo.challenge.controller.dto.CalculateResultDTO;
import com.tenpo.challenge.services.CalculateService;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

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

    public CalculateController(CalculateService calculateService) {
        this.calculateService = calculateService;
    }


    @PostMapping(path="/calculate")
    @RateLimiter(name = "calculate")
    public ResponseEntity<CalculateResultDTO> calculate(@Valid @RequestBody CalculateDTO requestCalculateDTO) {
         logger.info("CalculateController.calculate() params: ".concat(requestCalculateDTO.toString()));
         BigDecimal result = this.calculateService.calculate(requestCalculateDTO.getValueA(), requestCalculateDTO.getValueB());
         return new ResponseEntity<>(new CalculateResultDTO(result),HttpStatus.OK);
    }


}
