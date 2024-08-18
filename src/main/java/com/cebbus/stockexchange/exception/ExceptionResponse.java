package com.cebbus.stockexchange.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExceptionResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String message;
    private final String path;
}
