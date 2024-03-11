package com.avi.transaction.dto;


import com.avi.transaction.model.Transaction;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionResponse {
    private UUID id;
    private Transaction.TransactionType type;
    private BigDecimal amount;
    private String description;
    private LocalDate transactionDate;
}
