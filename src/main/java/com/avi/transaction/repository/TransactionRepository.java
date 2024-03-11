package com.avi.transaction.repository;

import com.avi.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByType(String type);
    List<Transaction> findByDateBetween(LocalDate from, LocalDate to);
    List<Transaction> findByAmountGreaterThanEqual(BigDecimal amount);
}

