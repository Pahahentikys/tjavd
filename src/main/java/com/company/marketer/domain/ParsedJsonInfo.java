package com.company.marketer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE)
public class ParsedJsonInfo {
    private List<Integer> timestamp;

    private List<BigDecimal> open;

    private List<BigDecimal> high;

    private List<BigDecimal> close;
}
