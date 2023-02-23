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

@Service
@RequiredArgsConstructor
public class JsonProccessingServiceImpl implements JsonProccessingService {
    private final ObjectMapper objectMapper;

    @NonNull
    public ParsedJsonInfo getParsedInfo(@NonNull String jsonFileName) {
            var root = getRootOfJson(jsonFileName);

            var chart = root.get("chart");
            var result = chart.get("result");

            var timestamp = result.get(0).get("timestamp");

            var indicators = result.get(0).get("indicators");
            var quote = indicators.get("quote");

            var open = quote.get(0).get("open");
            var close = quote.get(0).get("close");
            var high = quote.get(0).get("high");

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
