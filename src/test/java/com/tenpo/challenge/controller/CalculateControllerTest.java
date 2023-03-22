package com.tenpo.challenge.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.challenge.controller.dto.CalculateDTO;
import com.tenpo.challenge.controller.dto.CalculateResultDTO;

import com.tenpo.challenge.exceptions.BusinessException;
import com.tenpo.challenge.external.PercentageClient;
import com.tenpo.challenge.external.dto.PercentageDTO;
import com.tenpo.challenge.redis.RedisClient;

import com.tenpo.challenge.services.CalculateRequestService;
import com.tenpo.challenge.services.CalculateService;
import com.tenpo.challenge.services.PercentageService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ExtendWith(MockitoExtension.class)
public class CalculateControllerTest {

    @Mock
    private RedisClient redisClient;

    @Mock
    private PercentageClient percentageClient;

    @Autowired
    private CalculateRequestService calculateRequestService;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        PercentageService percentageService = new PercentageService(percentageClient,redisClient);
        CalculateService calculateService = new CalculateService(calculateRequestService,percentageService);
        CalculateController calculateController = new CalculateController(calculateService);
        mockMvc = MockMvcBuilders.standaloneSetup(calculateController).build();
    }

    @Test
    public void calculateWithValueInRedis() throws Exception {
        when(redisClient.getNumber()).thenReturn(Optional.of(10L));
        final MvcResult result = this.mockMvc.perform(post("/calculate")
                        .content(this.objectMapper.writeValueAsString(new CalculateDTO(5L, 5L)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn();
        final CalculateResultDTO calculateResultDTO = this.objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        Assertions.assertEquals(calculateResultDTO.getValue() , new BigDecimal(11));
    }

    @Test
    public void calculateWithValueInPercentageClient() throws Exception {
        when(redisClient.getNumber()).thenReturn(Optional.empty());
        when(percentageClient.getPercentage()).thenReturn(new PercentageDTO(4L));
        final MvcResult result = this.mockMvc.perform(post("/calculate")
                        .content(this.objectMapper.writeValueAsString(new CalculateDTO(1L, 3L)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        final CalculateResultDTO calculateResultDTO = this.objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        Assertions.assertEquals(calculateResultDTO.getValue() , new BigDecimal(4));
    }

    @Test
    public void calculateWithLastValue() throws Exception {
        when(redisClient.getNumber()).thenReturn(Optional.empty());
        when(percentageClient.getPercentage()).thenThrow(new BusinessException("error"));
        when(redisClient.getLastNumber()).thenReturn(Optional.of(11L));
        final MvcResult result = this.mockMvc.perform(post("/calculate")
                        .content(this.objectMapper.writeValueAsString(new CalculateDTO(1L, 3L)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        final CalculateResultDTO calculateResultDTO = this.objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        Assertions.assertEquals(calculateResultDTO.getValue() , new BigDecimal(4));
    }

}
