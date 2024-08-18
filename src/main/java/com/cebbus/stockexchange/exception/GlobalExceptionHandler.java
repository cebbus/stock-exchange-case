package com.cebbus.stockexchange.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({StockNotFoundException.class, StockExchangeNotFoundException.class, EmptyResultDataAccessException.class})
    public final ResponseEntity<ExceptionResponse> handleStockNotFoundException(
            RuntimeException ex,
            WebRequest request) {
        return createResponse(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ExceptionResponse> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex,
            WebRequest request) {
        return createResponse(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ExceptionResponse> handleDataIntegrityViolationException(
            MethodArgumentNotValidException ex,
            WebRequest request) {
        log.error(ex.getMessage(), ex);

        String errors = ex.getBindingResult().getAllErrors().stream().map(e -> {
            String fieldName = ((FieldError) e).getField();
            String errorMessage = e.getDefaultMessage();

            return "[" + fieldName + "]: " + errorMessage;
        }).collect(Collectors.joining(System.lineSeparator()));

        String message = "Validation failed: " + errors;

        return createResponse(request, HttpStatus.BAD_REQUEST, message);
    }

    private ResponseEntity<ExceptionResponse> createResponse(Exception ex, WebRequest request, HttpStatus status) {
        log.error(ex.getMessage(), ex);
        return createResponse(request, status, ex.getMessage());
    }

    private ResponseEntity<ExceptionResponse> createResponse(WebRequest request, HttpStatus status, String message) {
        ExceptionResponse response = new ExceptionResponse(
                status.value(), status.getReasonPhrase(),
                message, request.getDescription(false));

        return new ResponseEntity<>(response, status);
    }

}
