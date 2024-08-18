package com.cebbus.stockexchange.exception;

public class StockExchangeNotFoundException extends RuntimeException {
    public StockExchangeNotFoundException(String name) {
        super(String.format("Stock exchange not found with the specified name: %s", name));
    }
}
