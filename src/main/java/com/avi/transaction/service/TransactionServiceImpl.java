package com.avi.transaction.service;

import com.avi.transaction.dto.BetweenDatesRequest;
import com.avi.transaction.dto.TransactionRequest;
import com.avi.transaction.dto.TransactionResponse;
import com.avi.transaction.dto.TransactionSummary;
import com.avi.transaction.exception.CurrencyConversionException;
import com.avi.transaction.exception.TransactionException;
import com.avi.transaction.model.Transaction;
import com.avi.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final CurrencyConversionService currencyConversionService;

    @Override
    public List<TransactionResponse> getTransactions() {
        List<TransactionResponse> transactionResponseList = transactionRepository.findAll().stream()
                .map(this::convertToTransactionResponse)
                .toList();

        if (transactionResponseList.isEmpty()) {
            log.info("No transactions at all!");
            throw new TransactionException("No transactions at all!", HttpStatus.NO_CONTENT);
        }

        return transactionResponseList;
    }

    @Override
    public List<TransactionResponse> getTransactionsByType(String type) {
        List<TransactionResponse> transactionResponseList = transactionRepository.findAllByType(type).stream()
                .map(this::convertToTransactionResponse)
                .toList();

        if (transactionResponseList.isEmpty()) {
            log.info("No transactions for type: {}", type);
            throw new TransactionException("No transactions for type: " + type, HttpStatus.NO_CONTENT);
        }

        return transactionResponseList;
    }

    @Override
    public TransactionResponse getTransaction(Long id) {
        return convertToTransactionResponse(getTransactionModel(id));
    }

    public List<TransactionResponse> getTransactionsGEAmount(BigDecimal amount) {
        List<TransactionResponse> transactionResponseList = transactionRepository.findByAmountGreaterThanEqual(amount)
                .stream()
                .map(this::convertToTransactionResponse)
                .toList();

        if (transactionResponseList.isEmpty()) {
            log.info("No transactions that are greater or equal amount: {}", amount);
            throw new TransactionException("No transactions that are greater or equal amount: " + amount, HttpStatus.NO_CONTENT);
        }

        return transactionResponseList;

    }

    public List<TransactionResponse> getTransactionBetweenDates(BetweenDatesRequest betweenDatesRequest) {
        LocalDate from = betweenDatesRequest.getFrom();
        LocalDate to = betweenDatesRequest.getTo();

        List<TransactionResponse> transactionResponseList = transactionRepository
                .findByDateBetween(from, to).stream()
                .map(this::convertToTransactionResponse)
                .toList();

        if (transactionResponseList.isEmpty()) {
            log.info("No transactions between dates:{} to {}", from, to);
            throw new TransactionException(String.format("No transactions between dates:%s to %s", from, to),
                    HttpStatus.NO_CONTENT);
        }

        return transactionResponseList;
    }

    @Override
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {
        try {
            Transaction transaction = new Transaction();

            setModelWithRequest(transactionRequest, transaction);

            transaction = transactionRepository.save(transaction);
            log.info("Transaction {} is saved!", transaction);

            return convertToTransactionResponse(transaction);

        } catch (Exception ex) {
            throwCouldNotSaveException(ex);
        }
        return null;
    }


    @Override
    public TransactionResponse updateTransaction(Long id, TransactionRequest transactionRequest) {
        Transaction transaction = getTransactionModel(id);
        try {
            setModelWithRequest(transactionRequest, transaction);

            transaction = transactionRepository.save(transaction);
            log.info("Transaction {} is saved!", transaction);

            return convertToTransactionResponse(transaction);

        } catch (Exception ex) {
            throwCouldNotSaveException(ex);
        }
        return null;
    }


    @Override
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public TransactionSummary getTransactionsSummary() {

        List<Transaction> creditTransactions = transactionRepository.findAllByType("credit");
        List<Transaction> debitTransactions = transactionRepository.findAllByType("debit");

        BigDecimal totalCredit = creditTransactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDebit = debitTransactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal netBalance = totalCredit.add(totalDebit);

        return new TransactionSummary(totalCredit, totalDebit, netBalance);
    }

    @Override
    public BigDecimal getTransactionAmountInCurrency(Long id, Currency currency) {
        return null;
        Transaction transaction = getTransactionModel(id);

        try {
            BigDecimal transactionAmount = transaction.getAmount();
            BigDecimal conversionRate = currencyConversionService.getConversionRate(transactionCurrency, currency);

            return transactionAmount.multiply(conversionRate);
        } catch (Exception ex) {
            log.error("Currency conversion exception: " + ex.getMessage());
            throw new CurrencyConversionException("Could not convert: " + ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Transaction getTransactionModel(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Transaction with id {} doesn't exist", id);
                    return new TransactionException("Transaction doesn't exist!", HttpStatus.NOT_FOUND);
                });
    }

    private TransactionResponse convertToTransactionResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setType(transaction.getType());
        response.setAmount(transaction.getAmount());
        response.setDescription(transaction.getDescription());
        response.setTransactionDate(transaction.getDate());
        return response;
    }

    private void setModelWithRequest(TransactionRequest transactionRequest, Transaction transaction) {
        transaction.setType(transactionRequest.getType().toLowerCase());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setDescription(transactionRequest.getDescription());
        transaction.setDate(transactionRequest.getTransactionDate());
    }

    private void throwCouldNotSaveException(Exception ex) {
        log.error("Transaction could not be saved: " + ex.getMessage());
        throw new TransactionException("Transaction could not be saved: " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}