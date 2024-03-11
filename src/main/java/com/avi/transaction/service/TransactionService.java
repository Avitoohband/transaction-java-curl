package com.avi.transaction.service;

import com.avi.transaction.dto.TransactionRequest;
import com.avi.transaction.dto.TransactionResponse;
import com.avi.transaction.dto.TransactionSummary;
import com.avi.transaction.model.Transaction;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

import static com.avi.transaction.model.Transaction.*;

public interface TransactionService {
    List<TransactionResponse> getTransactions();

    List<TransactionResponse> getTransactionsByType(TransactionType type);

    TransactionResponse getTransaction(UUID id);

    TransactionResponse createTransaction(TransactionRequest transaction);

    TransactionResponse updateTransaction(UUID id, TransactionRequest transaction);

    void deleteTransaction(UUID id);

    TransactionSummary getTransactionsSummary();
}
