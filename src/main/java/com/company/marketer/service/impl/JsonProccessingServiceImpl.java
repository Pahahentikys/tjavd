package com.company.marketer.service.impl;


import com.company.marketer.domain.ParsedJsonInfo;
import com.company.marketer.service.JsonProccessingService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static com.company.marketer.enums.JsonNodeName.*;

@Service
@RequiredArgsConstructor
public class JsonProccessingServiceImpl implements JsonProccessingService {
    private static final int FIRST_IDX_IN_ARR = 0;

    private final ObjectMapper objectMapper;

    @NonNull
    public ParsedJsonInfo parseJsonFile(@NonNull String jsonFileName) {
            var root = getRootOfJson(jsonFileName);

            var chart = root.get(CHART.getValue());
            var result = chart.get(RESULT.getValue());

            var timestamp = result.get(FIRST_IDX_IN_ARR).get(TIMESTAMP.getValue());

            var indicators = result.get(FIRST_IDX_IN_ARR).get(INDICATORS.getValue());
            var quote = indicators.get(QUOTE.getValue());

            var open = quote.get(FIRST_IDX_IN_ARR).get(OPEN.getValue());
            var close = quote.get(FIRST_IDX_IN_ARR).get(CLOSE.getValue());
            var high = quote.get(FIRST_IDX_IN_ARR).get(HIGH.getValue());

        return buildParsedJsonInfo(timestamp, open, close, high);
    }

    private ParsedJsonInfo buildParsedJsonInfo(@NonNull JsonNode timestamp, @NonNull JsonNode open, @NonNull JsonNode close, @NonNull JsonNode high) {
        var listOfTimestamps = convertJsonNodeToListWithSuitableType(timestamp, new TypeReference<List<Integer>>(){});

        var listOfOpenPrices = convertJsonNodeToListWithSuitableType(open, new TypeReference<List<BigDecimal>>(){});

        var listOfClosePrises = convertJsonNodeToListWithSuitableType(close, new TypeReference<List<BigDecimal>>(){});

        var listOfHighPrices = convertJsonNodeToListWithSuitableType(high, new TypeReference<List<BigDecimal>>(){});

        return new ParsedJsonInfo(listOfTimestamps, listOfOpenPrices, listOfHighPrices, listOfClosePrises);
    }

    private JsonNode getRootOfJson(String jsonFileName) {
        var inputStream = JsonProccessingServiceImpl.class.getClassLoader().getResourceAsStream(jsonFileName);

        try {
            return objectMapper.readTree(inputStream);
        } catch (IOException ex) {
            throw new IllegalStateException("Getting json root error", ex);
        }
    }

    private <T> List<T> convertJsonNodeToListWithSuitableType(JsonNode jsonNode, TypeReference<List<T>> typeReference) {
        return objectMapper.convertValue(jsonNode, typeReference);
    }
}
