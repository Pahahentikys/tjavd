package com.company.marketer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Entity with main info about an actions company prices
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table
public class CompanyInfo {
    @PrimaryKey
    private UUID uuid;

    private ZonedDateTime zonedDateTime;

    private String name;

    private BigDecimal closePrice;

    private BigDecimal highPrice;

    private BigDecimal lowPrice;
}
