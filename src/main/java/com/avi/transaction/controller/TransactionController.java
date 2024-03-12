package com.avi.transaction.controller;

import com.avi.transaction.dto.BetweenDatesRequest;
import com.avi.transaction.dto.TransactionRequest;
import com.avi.transaction.dto.TransactionResponse;
import com.avi.transaction.dto.TransactionSummary;
import com.avi.transaction.service.TransactionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

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

    @GetMapping("/type")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TransactionResponse>> getTransactionsByType(@RequestParam String type) {
        return ResponseEntity.ok(transactionService.getTransactionsByType(type));
    }

    @GetMapping("/amount")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TransactionResponse>> getTransactionsGEAmount(@RequestParam BigDecimal amount) {
        return ResponseEntity.ok(transactionService.getTransactionsGEAmount(amount));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransaction(id));
    }

    @GetMapping("/dates")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TransactionResponse>> getTransactionBetweenDates(@RequestBody BetweenDatesRequest betweenDatesRequest) {
        return ResponseEntity.ok(transactionService.getTransactionBetweenDates(betweenDatesRequest));
    }

    @GetMapping("/amount-in-currency/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BigDecimal> getTransactionAmountInCurrency(@PathVariable Long id,
                                                                     @RequestParam Currency currency) {
        return ResponseEntity.ok(transactionService.getTransactionAmountInCurrency(id, currency));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        return ResponseEntity.ok(transactionService.createTransaction(transactionRequest));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TransactionResponse> updateTransaction(@PathVariable Long id,
                                                                 @RequestBody TransactionRequest transactionRequest) {
        return ResponseEntity.ok(transactionService.updateTransaction(id, transactionRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/summary")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TransactionSummary> getTransactionsSummary() {
        return ResponseEntity.ok(transactionService.getTransactionsSummary());
    }

}
