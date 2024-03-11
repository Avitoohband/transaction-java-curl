package com.avi.transaction.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionSummary {
    private BigDecimal totalDebit;
    private BigDecimal totalCredit;
    private BigDecimal netBalance;
}

