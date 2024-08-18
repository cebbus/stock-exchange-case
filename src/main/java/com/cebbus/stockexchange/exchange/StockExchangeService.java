package com.cebbus.stockexchange.exchange;

public interface StockExchangeService {
    StockExchange getStockExchangeByName(String name);

    StockExchange addStockToExchange(String exchangeName, String stockName);

    void deleteStockFromExchange(String exchangeName, String stockName);
}
