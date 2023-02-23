package com.company.marketer.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JsonNodeName {
    CHART("chart"),

    RESULT("result"),

    TIMESTAMP("timestamp"),

    INDICATORS("indicators"),

    QUOTE("quote"),

    OPEN("open"),

    CLOSE("close"),

    HIGH("high");

    private final String value;
}

