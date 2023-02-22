package com.company.marketer.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
@RequiredArgsConstructor
public class JsonUtil {
    private final ObjectMapper objectMapper;

    public <T> T parseJsonToObject(String jsonString, Class<T> valueType) {
        try {
            return objectMapper.readValue(jsonString.getBytes(UTF_8), valueType);
        } catch (IOException ex) {
            throw new IllegalStateException("Convert json to Entity error", ex);
        }
    }
}
