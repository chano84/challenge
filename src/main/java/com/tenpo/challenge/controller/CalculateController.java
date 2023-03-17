package com.tenpo.challenge.controller;

import com.tenpo.challenge.controller.dto.CalculateRequestDTO;
import com.tenpo.challenge.controller.dto.CalculateResponseDTO;
import com.tenpo.challenge.services.CalculateService;
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
    public CalculateResponseDTO calculate(@RequestBody CalculateRequestDTO requestCalculateDTO){
        BigDecimal result = this.calculateService.calculate(requestCalculateDTO.getValueA(), requestCalculateDTO.getValueB());
        return new CalculateResponseDTO(result);
    }

}
