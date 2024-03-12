package com.avi.transaction.service;

import com.avi.transaction.dto.BetweenDatesRequest;
import com.avi.transaction.dto.TransactionRequest;
import com.avi.transaction.dto.TransactionResponse;
import com.avi.transaction.dto.TransactionSummary;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

public interface TransactionService {
    List<TransactionResponse> getTransactions();

    List<TransactionResponse> getTransactionsByType(String type);

    TransactionResponse getTransaction(Long id);

    List<TransactionResponse> getTransactionsGEAmount(BigDecimal amount);

    List<TransactionResponse> getTransactionBetweenDates(BetweenDatesRequest betweenDatesRequest);

    TransactionResponse createTransaction(TransactionRequest transaction);

    TransactionResponse updateTransaction(Long id, TransactionRequest transaction);

    void deleteTransaction(Long id);

    BigDecimal getTransactionAmountInCurrency (Long id, Currency currency);
    TransactionSummary getTransactionsSummary();
}
