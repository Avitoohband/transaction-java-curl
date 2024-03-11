package com.avi.transaction.service;

import com.avi.transaction.dto.TransactionRequest;
import com.avi.transaction.dto.TransactionResponse;
import com.avi.transaction.dto.TransactionSummary;
import com.avi.transaction.exception.TransactionException;
import com.avi.transaction.model.Transaction;
import com.avi.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Override
    public List<TransactionResponse> getTransactions() {
        return transactionRepository.findAll().stream()
                .map(this::convertToTransactionResponse)
                .toList();
    }

    @Override
    public List<TransactionResponse> getTransactionsByType(Transaction.TransactionType type) {
        return transactionRepository.findAllByType(type).stream()
                .map(this::convertToTransactionResponse)
                .toList();
    }

    @Override
    public TransactionResponse getTransaction(UUID id) {
        return convertToTransactionResponse(getTransactionModel(id));
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
    public TransactionResponse updateTransaction(UUID id, TransactionRequest transactionRequest) {
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
    public void deleteTransaction(UUID id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public TransactionSummary getTransactionsSummary() {
        // Implement the logic to calculate totalDebit, totalCredit, and netBalance
        BigDecimal totalDebit = BigDecimal.ZERO; // Placeholder for calculation
        BigDecimal totalCredit = BigDecimal.ZERO; // Placeholder for calculation
        BigDecimal netBalance = totalCredit.subtract(totalDebit);

        return new TransactionSummary(totalDebit, totalCredit, netBalance);
    }

    private Transaction getTransactionModel(UUID id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Transaction with id {} doesn't exist", id);
                    return new TransactionException("Transaction Doesn't exist!", HttpStatus.NOT_FOUND);
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
        transaction.setType(transactionRequest.getType());
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
