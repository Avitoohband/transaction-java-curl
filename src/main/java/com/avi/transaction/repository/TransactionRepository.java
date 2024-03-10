package com.avi.transaction.repository;

import com.avi.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.avi.transaction.model.Transaction.*;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllType(Type type);
    List<Transaction> findByTransactionDateBetween(LocalDate start, LocalDate end);
    List<Transaction> findByAmountGreaterThanEqual(BigDecimal amount);
}

