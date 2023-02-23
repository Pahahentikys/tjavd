package com.company.marketer.service;

import com.company.marketer.service.impl.JsonProccessingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Json processing test")
class JsonProccessingServiceImplTest {

    private JsonProccessingService jsonProccessingService;


    @BeforeEach
    void setUp() {
        jsonProccessingService = new JsonProccessingServiceImpl();
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
