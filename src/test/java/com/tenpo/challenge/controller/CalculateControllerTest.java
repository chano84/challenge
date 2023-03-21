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

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class CalculateControllerTest {

    @Mock
    private RedisClient redisClient;

    @Mock
    private PercentageClient percentageClient;

    @Mock
    private CalculateRequestService calculateRequestService;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private PercentageService percentageService;

    private CalculateService calculateService;

    @BeforeEach
    public void setUp() {
        percentageService = new PercentageService(percentageClient,redisClient);
        calculateService = new CalculateService(calculateRequestService,percentageService);
        CalculateController calculateController = new CalculateController(calculateService);
        mockMvc = MockMvcBuilders.standaloneSetup(calculateController).build();
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void calculateWithValueInRedis() throws Exception {
        when(redisClient.getNumber()).thenReturn(Optional.ofNullable(Long.valueOf(2)));
        final MvcResult result = this.mockMvc.perform(post("/calculate")
                        .content(this.objectMapper.writeValueAsString(new CalculateDTO(Long.valueOf(1), Long.valueOf(3))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn();
        final CalculateResultDTO calculateResultDTO = this.objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<CalculateResultDTO>() {});
        Assertions.assertEquals(calculateResultDTO.getValue() , new BigDecimal(6));
    }


    /**
     *
     * @throws Exception
     */
    @Test
    public void calculateWithValueInPercentageClient() throws Exception {
        when(redisClient.getNumber()).thenReturn(Optional.ofNullable(null));
        when(percentageClient.getPercentage()).thenReturn(new PercentageDTO(Long.valueOf(2)));
        final MvcResult result = this.mockMvc.perform(post("/calculate")
                        .content(this.objectMapper.writeValueAsString(new CalculateDTO(Long.valueOf(1), Long.valueOf(3))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        final CalculateResultDTO calculateResultDTO = this.objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<CalculateResultDTO>() {});
        Assertions.assertEquals(calculateResultDTO.getValue() , new BigDecimal(6));
    }


    /**
     *
     * @throws Exception
     */
    @Test
    public void calculateWithLastValue() throws Exception {
        when(redisClient.getNumber()).thenReturn(Optional.ofNullable(null));
        when(percentageClient.getPercentage()).thenThrow(new BusinessException("error"));
        when(redisClient.getLastNumber()).thenReturn(Optional.ofNullable(Long.valueOf(2)));
        final MvcResult result = this.mockMvc.perform(post("/calculate")
                        .content(this.objectMapper.writeValueAsString(new CalculateDTO(Long.valueOf(1), Long.valueOf(3))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        final CalculateResultDTO calculateResultDTO = this.objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<CalculateResultDTO>() {});
        Assertions.assertEquals(calculateResultDTO.getValue() , new BigDecimal(6));
    }


}
