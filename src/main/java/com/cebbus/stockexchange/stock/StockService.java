package com.cebbus.stockexchange.stock;

public interface StockService {
    Stock createStock(Stock stock);

    Stock updatePrice(Long id, UpdatePriceRequest request);

    void deleteStock(Long id);

    Stock getByNameJoinExchangesAndLockRow(String name);
}
