package com.avi.transaction.service;

import com.avi.transaction.dto.TransactionRequest;
import com.avi.transaction.dto.TransactionResponse;
import com.avi.transaction.dto.TransactionSummary;
import com.avi.transaction.exception.TransactionException;
import com.avi.transaction.model.Transaction;
import com.avi.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
class TransactionServiceImplTest {
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;


    @Test
    void getTransactionsSuccess() {
        when(transactionRepository.findAll()).thenReturn(List.of(new Transaction(1L, "credit",
                BigDecimal.valueOf(100), "Salary", LocalDate.now())));

        List<TransactionResponse> transactions = transactionService.getTransactions();

        assertEquals(1, transactions.size());
        assertEquals(BigDecimal.valueOf(100), transactions.get(0).getAmount());
    }

    @Test
    void createTransactionSuccess() {
        TransactionRequest request = new TransactionRequest("debit", BigDecimal.valueOf(50), "Groceries", LocalDate.now());
        Transaction savedTransaction = new Transaction(1L, request.getType(), request.getAmount(),
                request.getDescription(), request.getTransactionDate());

        when(transactionRepository.save(any(Transaction.class))).thenReturn(savedTransaction);

        TransactionResponse response = transactionService.createTransaction(request);

        assertEquals(BigDecimal.valueOf(50), response.getAmount());
        assertEquals("debit", response.getType());
    }

    @Test
    void getTransactionsSummarySuccess() {
        List<Transaction> credits = List.of(new Transaction(1L, "credit", BigDecimal.valueOf(200),
                "Salary", LocalDate.now()));
        List<Transaction> debits = List.of(new Transaction(2L, "debit", BigDecimal.valueOf(100),
                "Groceries", LocalDate.now()));

        when(transactionRepository.findAllByType("credit")).thenReturn(credits);
        when(transactionRepository.findAllByType("debit")).thenReturn(debits);

        TransactionSummary summary = transactionService.getTransactionsSummary();

        assertEquals(BigDecimal.valueOf(200), summary.getTotalCredit());
        assertEquals(BigDecimal.valueOf(100), summary.getTotalDebit());
        assertEquals(BigDecimal.valueOf(300), summary.getNetBalance());
    }

    @Test
    void getTransactionsShouldThrow() {
        when(transactionRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(TransactionException.class, () -> transactionService.getTransactions(),
                "No transactions at all!");
    }
}