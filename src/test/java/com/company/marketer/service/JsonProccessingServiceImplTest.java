package com.company.marketer.service;

import com.company.marketer.service.impl.JsonProccessingServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Json processing test")
class JsonProccessingServiceImplTest {

    private ObjectMapper objectMapper;

    private JsonProccessingServiceImpl jsonProccessingService;


    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        jsonProccessingService = new JsonProccessingServiceImpl(objectMapper);
    }

    @Test
    void getParsedInfoTest() {
        var parsedInfo = jsonProccessingService.getParsedInfo("AAL.json");

        int expectedArraySize = 4333;

        assertEquals(expectedArraySize, parsedInfo.getClose().size());
        assertEquals(expectedArraySize, parsedInfo.getOpen().size());
        assertEquals(expectedArraySize, parsedInfo.getHigh().size());
    }
}
