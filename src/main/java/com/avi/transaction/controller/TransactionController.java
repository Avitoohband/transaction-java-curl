package com.avi.transaction.controller;

import com.avi.transaction.dto.TransactionExceptionResponse;
import com.avi.transaction.dto.TransactionRequest;
import com.avi.transaction.dto.TransactionResponse;
import com.avi.transaction.dto.TransactionSummary;
import com.avi.transaction.exception.TransactionException;
import com.avi.transaction.service.TransactionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.avi.transaction.model.Transaction.TransactionType;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor

public class TransactionController {
    private final TransactionServiceImpl transactionService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getTransactions());
    }

    @GetMapping("/type/{type}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TransactionResponse>> getTransactionsByType(@PathVariable TransactionType type) {
        return ResponseEntity.ok(transactionService.getTransactionsByType(type));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable UUID id) {
        return ResponseEntity.ok(transactionService.getTransaction(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        return ResponseEntity.ok(transactionService.createTransaction(transactionRequest));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TransactionResponse> updateTransaction(@PathVariable UUID id,
                                                                 @RequestBody TransactionRequest transactionRequest) {
        return ResponseEntity.ok(transactionService.updateTransaction(id, transactionRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteTransaction(@PathVariable UUID id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/summary")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TransactionSummary> getTransactionsSummary() {
        return ResponseEntity.ok(transactionService.getTransactionsSummary());
    }

    @ExceptionHandler()
    private ResponseEntity<TransactionExceptionResponse> handleException(TransactionException ex) {
        TransactionExceptionResponse errorResponse = new TransactionExceptionResponse(
                ex.getStatus().value(), ex.getMessage()
        );

        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }

}
