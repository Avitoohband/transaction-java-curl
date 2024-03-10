package com.avi.transaction.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Type type; // "debit" or "credit"
    private BigDecimal amount;
    private String description;
    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    public enum Type {
        DEBIT, CREDIT
    }
}
