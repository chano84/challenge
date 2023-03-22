package com.tenpo.challenge.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.challenge.controller.dto.CalculateDTO;
import com.tenpo.challenge.controller.dto.PageDTO;
import com.tenpo.challenge.external.PercentageClient;
import com.tenpo.challenge.redis.RedisClient;
import com.tenpo.challenge.services.CalculateRequestService;
import com.tenpo.challenge.services.CalculateService;
import com.tenpo.challenge.services.PercentageService;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
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

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ExtendWith(MockitoExtension.class)
public class CalculateRequestControllerTest {

    @Mock
    private RedisClient redisClient;

    @Mock
    private PercentageClient percentageClient;

    @Autowired
    private CalculateRequestService calculateRequestService;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected MockMvc mockMvcRequest;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RateLimiterRegistry rateLimiterRegistry;

    @BeforeEach
    public void setUp() {
        PercentageService percentageService = new PercentageService(percentageClient,redisClient);
        CalculateService calculateService = new CalculateService(calculateRequestService,percentageService);
        CalculateController calculateController = new CalculateController(calculateService, rateLimiterRegistry);
        CalculateRequestController calculateRequestController = new CalculateRequestController(calculateRequestService);
        mockMvc = MockMvcBuilders.standaloneSetup(calculateController).build();
        mockMvcRequest = MockMvcBuilders.standaloneSetup(calculateRequestController).build();
    }

    @Test
    public void calculateWithValueInRedis() throws Exception {
        when(redisClient.getNumber()).thenReturn(Optional.of(2L));
        this.mockMvc.perform(post("/calculate")
                        .content(this.objectMapper.writeValueAsString(new CalculateDTO(1L, 3L)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        final MvcResult resultRequest = this.mockMvcRequest.perform(get("/calculate-request")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        final PageDTO calculateResultDTO = this.objectMapper.readValue(resultRequest.getResponse().getContentAsString(), new TypeReference<>() {});
        Assertions.assertEquals(calculateResultDTO.getNumber() , 0);
        Assertions.assertEquals(calculateResultDTO.getNumberOfElements() , 1);
        Assertions.assertEquals(calculateResultDTO.getTotalPages() , 1);

    }
}
