package com.avi.transaction.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private TransactionType type; // "debit" or "credit"
    private BigDecimal amount;
    private String description;
    @Column(name = "transaction_date")
    private LocalDate date;

    public enum TransactionType {
        DEBIT, CREDIT
    }
}
