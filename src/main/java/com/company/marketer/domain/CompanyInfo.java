package com.company.marketer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Entity with main info about an actions company prices
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(value = "company_info")
public class CompanyInfo {
    @PrimaryKey
    private UUID uuid;

    private LocalDate date;

    private String name;

    private BigDecimal closePrice;

    private BigDecimal highPrice;

    private BigDecimal lowPrice;
}
