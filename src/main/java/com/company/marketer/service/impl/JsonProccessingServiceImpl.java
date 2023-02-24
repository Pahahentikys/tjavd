package com.company.marketer.service.impl;


import com.company.marketer.domain.ParsedJsonInfo;
import com.company.marketer.service.JsonProccessingService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import static com.company.marketer.enums.JsonNodeName.*;

@Service
@RequiredArgsConstructor
public class JsonProccessingServiceImpl implements JsonProccessingService {
    private static final int FIRST_IDX_IN_ARR = 0;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private final ResourceLoader resourceLoader;

    public Mono<ParsedJsonInfo> parseJsonFile(@NonNull String jsonFileName) {
        var root = getRootOfJson(jsonFileName);

        var chart = root.get(CHART.getValue());
        var result = chart.get(RESULT.getValue());

        var timestamp = result.get(FIRST_IDX_IN_ARR).get(TIMESTAMP.getValue());

        var indicators = result.get(FIRST_IDX_IN_ARR).get(INDICATORS.getValue());
        var quote = indicators.get(QUOTE.getValue());

        var low = quote.get(FIRST_IDX_IN_ARR).get(LOW.getValue());
        var close = quote.get(FIRST_IDX_IN_ARR).get(CLOSE.getValue());
        var high = quote.get(FIRST_IDX_IN_ARR).get(HIGH.getValue());

        return Mono.just(buildParsedJsonInfo(timestamp, low, close, high));
    }

    private ParsedJsonInfo buildParsedJsonInfo(@NonNull JsonNode timestamp, @NonNull JsonNode low, @NonNull JsonNode close, @NonNull JsonNode high) {
        var listOfDateTimes = convertJsonNodeToListWithSuitableType(timestamp, new TypeReference<List<ZonedDateTime>>(){});

        var listOfLowPrices = convertJsonNodeToListWithSuitableType(low, new TypeReference<List<BigDecimal>>(){});

        var listOfClosePrises = convertJsonNodeToListWithSuitableType(close, new TypeReference<List<BigDecimal>>(){});

        var listOfHighPrices = convertJsonNodeToListWithSuitableType(high, new TypeReference<List<BigDecimal>>(){});

        return new ParsedJsonInfo(listOfDateTimes, listOfLowPrices, listOfHighPrices, listOfClosePrises);
    }

    private JsonNode getRootOfJson(String jsonFileName) {
        var resource = resourceLoader.getResource("classpath:data/" + jsonFileName);

        try {
            var inputStream = resource.getInputStream();

            return objectMapper.readTree(inputStream);
        } catch (IOException ex) {
            throw new IllegalStateException("Getting json root error", ex);
        }
    }

    private <T> List<T> convertJsonNodeToListWithSuitableType(JsonNode jsonNode, TypeReference<List<T>> typeReference) {
        return objectMapper.convertValue(jsonNode, typeReference);
    }
}
