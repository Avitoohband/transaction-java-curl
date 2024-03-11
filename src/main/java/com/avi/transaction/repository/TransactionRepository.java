package com.avi.transaction.repository;

import com.avi.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.avi.transaction.model.Transaction.*;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findAllByType(TransactionType type);
    List<Transaction> findByDateBetween(LocalDate start, LocalDate end);
    List<Transaction> findByAmountGreaterThanEqual(BigDecimal amount);
}

