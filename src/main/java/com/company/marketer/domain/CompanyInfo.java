package com.company.marketer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;

/**
 * Entity with main info about an actions company prices
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table
public class CompanyInfo {
    @PrimaryKey
    private int id;

    private String name;

    private BigDecimal openPrice;

    private BigDecimal highPrice;

    private BigDecimal lowPrice;
}
