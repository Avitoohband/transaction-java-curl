package com.avi.transaction.dto;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionRequest {
    private String type;
    private BigDecimal amount;
    private String description;
    private LocalDate transactionDate;
}
