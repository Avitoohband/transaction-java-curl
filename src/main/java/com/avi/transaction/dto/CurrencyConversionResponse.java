package com.avi.transaction.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyConversionResponse {
    private boolean success;
    private long timestamp;
    private String base;
    private LocalDate date;
    private Map<String, BigDecimal> rates;
}
