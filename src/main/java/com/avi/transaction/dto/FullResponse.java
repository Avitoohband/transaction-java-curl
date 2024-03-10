package com.avi.transaction.dto;

import org.springframework.http.HttpStatus;

public class FullResponse<T> {
    T data;
    HttpStatus status;
}
