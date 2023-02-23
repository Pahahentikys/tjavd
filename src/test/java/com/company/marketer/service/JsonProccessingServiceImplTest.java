package com.company.marketer.service;

import com.company.marketer.service.impl.JsonProccessingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Json processing test")
class JsonProccessingServiceImplTest {

    private JsonProccessingService jsonProccessingService;

    private ResourceLoader resourceLoader;

    private Resource mockResource;


    @BeforeEach
    void setUp() {
        mockResource = mock(Resource.class);

        resourceLoader = mock(ResourceLoader.class);

        jsonProccessingService = new JsonProccessingServiceImpl(resourceLoader);
    }

    @Test
    @DisplayName("Test on method for json parsing")
    void parseJsonFileTest() throws IOException {
        var mockJson = "{\n" +
                "    \"chart\": {\n" +
                "        \"result\": [\n" +
                "            {\n" +
                "                \"meta\": {\n" +
                "                    \"currency\": \"USD\",\n" +
                "                    \"symbol\": \"AAL\",\n" +
                "                    \"exchangeName\": \"NMS\",\n" +
                "                    \"instrumentType\": \"EQUITY\",\n" +
                "                    \"firstTradeDate\": 1127827800,\n" +
                "                    \"regularMarketTime\": 1670862355,\n" +
                "                    \"gmtoffset\": -18000,\n" +
                "                    \"timezone\": \"EST\",\n" +
                "                    \"exchangeTimezoneName\": \"America/New_York\",\n" +
                "                    \"regularMarketPrice\": 13.915,\n" +
                "                    \"chartPreviousClose\": 19.3,\n" +
                "                    \"priceHint\": 2,\n" +
                "                    \"currentTradingPeriod\": {\n" +
                "                        \"pre\": {\n" +
                "                            \"timezone\": \"EST\",\n" +
                "                            \"start\": 1670835600,\n" +
                "                            \"end\": 1670855400,\n" +
                "                            \"gmtoffset\": -18000\n" +
                "                        },\n" +
                "                        \"regular\": {\n" +
                "                            \"timezone\": \"EST\",\n" +
                "                            \"start\": 1670855400,\n" +
                "                            \"end\": 1670878800,\n" +
                "                            \"gmtoffset\": -18000\n" +
                "                        },\n" +
                "                        \"post\": {\n" +
                "                            \"timezone\": \"EST\",\n" +
                "                            \"start\": 1670878800,\n" +
                "                            \"end\": 1670893200,\n" +
                "                            \"gmtoffset\": -18000\n" +
                "                        }\n" +
                "                    },\n" +
                "                    \"dataGranularity\": \"1d\",\n" +
                "                    \"range\": \"\",\n" +
                "                    \"validRanges\": [\n" +
                "                        \"1d\",\n" +
                "                        \"5d\",\n" +
                "                        \"1mo\",\n" +
                "                        \"3mo\",\n" +
                "                        \"6mo\",\n" +
                "                        \"1y\",\n" +
                "                        \"2y\",\n" +
                "                        \"5y\",\n" +
                "                        \"10y\",\n" +
                "                        \"ytd\",\n" +
                "                        \"max\"\n" +
                "                    ]\n" +
                "                },\n" +
                "                \"timestamp\": [\n" +
                "                    1127827800,\n" +
                "                    1127914200,\n" +
                "                    1128000600],\n" +
                "                \"events\": {\n" +
                "                    \"dividends\": {\n" +
                "                        \"1406813400\": {\n" +
                "                            \"amount\": 0.1,\n" +
                "                            \"date\": 1406813400\n" +
                "                        },\n" +
                "                        \"1414675800\": {\n" +
                "                            \"amount\": 0.1,\n" +
                "                            \"date\": 1414675800\n" +
                "                        }\n" +
                "                    }\n" +
                "                },\n" +
                "                \"indicators\": {\n" +
                "                    \"quote\": [\n" +
                "                        {\n" +
                "                            \"open\": [\n" +
                "                                21.049999237060547,\n" +
                "                                19.299999237060547,\n" +
                "                                20.399999618530273\n" +
                "                            ],\n" +
                "                            \"close\": [\n" +
                "                                19.299999237060547,\n" +
                "                                20.5,\n" +
                "                                20.209999084472656\n" +
                "                            ],\n" +
                "                            \"volume\": [\n" +
                "                                961200,\n" +
                "                                5747900,\n" +
                "                                1078200\n" +
                "                            ],\n" +
                "                            \"high\": [\n" +
                "                                21.399999618530273,\n" +
                "                                20.530000686645508,\n" +
                "                                20.579999923706055\n" +
                "                            ],\n" +
                "                            \"low\": [\n" +
                "                                19.100000381469727,\n" +
                "                                19.200000762939453,\n" +
                "                                20.100000381469727\n" +
                "                            ]\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"adjclose\": [\n" +
                "                        {\n" +
                "                            \"adjclose\": [\n" +
                "                                18.194910049438477,\n" +
                "                                19.326204299926758,\n" +
                "                                19.052804946899414\n" +
                "                            ]\n" +
                "                        }\n" +
                "                    ]\n" +
                "                }\n" +
                "            }\n" +
                "        ],\n" +
                "        \"error\": null\n" +
                "    }\n" +
                "}";

        var inputStream = new ByteArrayInputStream(mockJson.getBytes());


        when(resourceLoader.getResource(any())).thenReturn(mockResource);
        when(mockResource.getInputStream()).thenReturn(inputStream);

        var parsedInfo = jsonProccessingService.parseJsonFile("AAL.json");

        int expectedArraySize = 3;

        assertEquals(expectedArraySize, parsedInfo.getClosePrices().size());
        assertEquals(expectedArraySize, parsedInfo.getLowPrices().size());
        assertEquals(expectedArraySize, parsedInfo.getHighPrices().size());

        assertEquals(new BigDecimal("19.100000381469727"), parsedInfo.getLowPrices().get(0));
        assertEquals(new BigDecimal("21.399999618530273"), parsedInfo.getHighPrices().get(0));
        assertEquals(new BigDecimal("19.299999237060547"), parsedInfo.getClosePrices().get(0));

        verify(resourceLoader).getResource(any());
        verify(mockResource).getInputStream();
    }
}
