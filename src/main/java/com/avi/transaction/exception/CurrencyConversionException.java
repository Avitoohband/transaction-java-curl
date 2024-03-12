package com.avi.transaction.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CurrencyConversionException extends RuntimeException {
    private final HttpStatus status;

    public CurrencyConversionException(String message, HttpStatus status) {
        super(message);
        this.status = status;

    }
}
