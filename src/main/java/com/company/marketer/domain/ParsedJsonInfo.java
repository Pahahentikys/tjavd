package com.company.marketer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE)
public class ParsedJsonInfo {
    private List<ZonedDateTime> dateTimes;

    private List<BigDecimal> lowPrices;

    private List<BigDecimal> highPrices;

    private List<BigDecimal> closePrices;
}
