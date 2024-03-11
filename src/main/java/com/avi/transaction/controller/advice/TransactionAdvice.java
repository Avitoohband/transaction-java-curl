package com.avi.transaction.controller.advice;


import com.avi.transaction.dto.TransactionExceptionResponse;
import com.avi.transaction.exception.TransactionException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TransactionAdvice {

    @ExceptionHandler(TransactionException.class)
    private ResponseEntity<TransactionExceptionResponse> handleException(TransactionException ex) {

        TransactionExceptionResponse errorResponse = new TransactionExceptionResponse(
                ex.getStatus().value(), ex.getMessage()
        );

        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }
}
