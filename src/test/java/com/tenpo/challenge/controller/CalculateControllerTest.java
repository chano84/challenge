package com.tenpo.challenge.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.challenge.controller.dto.CalculateDTO;
import com.tenpo.challenge.controller.dto.CalculateResultDTO;

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

    private CalculateService calculateService;

    @Mock
    private RedisClient redisClient;

    @Mock
    private PercentageClient percentageClient;

    @Mock
    private CalculateRequestService calculateRequestService;

    private PercentageService percentageService;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


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
    public void calculateTest() throws Exception {
        when(redisClient.getNumber()).thenReturn(Optional.ofNullable(Long.valueOf(2)));
//        when(percentageClient.getPercentage()).thenReturn(new PercentageDTO().setValue(Long.valueOf(2)));
        final MvcResult result = this.mockMvc.perform(post("/calculate")
                        .content(asJsonString(new CalculateDTO(Long.valueOf(1), Long.valueOf(3))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn();
        final CalculateResultDTO calculateResultDTO = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<CalculateResultDTO>() {});
        Assertions.assertEquals(calculateResultDTO.getValue() , new BigDecimal(6));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
//
//    /**
//     *
//     * @throws Exception
//     */
//    @Test
//    public void calculateWithPercentageClient() throws Exception {
//        final MvcResult result = this.mockMvc.perform(get("/calculate"))
//                .andReturn();
//        final CalculateResultDTO calculateResultDTO = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<CalculateResultDTO>() {});
//        Assertions.assertEquals(calculateResultDTO , 1);
//    }
//
//    /**
//     *
//     * @throws Exception
//     */
//    @Test
//    public void calculateWithPercentageLastValue() throws Exception {
//        final MvcResult result = this.mockMvc.perform(get("/calculate"))
//                .andReturn();
//        final CalculateResultDTO calculateResultDTO = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<CalculateResultDTO>() {});
//        Assertions.assertEquals(calculateResultDTO , 1);
//    }

}
