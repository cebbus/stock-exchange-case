package com.cebbus.stockexchange.exception;

public class StockNotFoundException extends RuntimeException {

    public StockNotFoundException(Long id) {
        super(String.format("Stock not found with the specified id: %d", id));
    }

    public StockNotFoundException(String name) {
        super(String.format("Stock not found with the specified name: %s", name));
    }
}
