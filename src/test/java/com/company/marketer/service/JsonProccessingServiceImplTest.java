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
    @DisplayName("Test on method for json parsing")
    void parseJsonFileTest() {
        var parsedInfo = jsonProccessingService.parseJsonFile("AAL.json");

        int expectedArraySize = 4333;

        assertEquals(expectedArraySize, parsedInfo.getClosePrices().size());
        assertEquals(expectedArraySize, parsedInfo.getLowPrices().size());
        assertEquals(expectedArraySize, parsedInfo.getHighPrices().size());
    }
}
