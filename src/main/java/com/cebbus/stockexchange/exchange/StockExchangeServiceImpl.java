package com.cebbus.stockexchange.exchange;

import com.cebbus.stockexchange.exception.StockExchangeNotFoundException;
import com.cebbus.stockexchange.stock.Stock;
import com.cebbus.stockexchange.stock.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
public class StockExchangeServiceImpl implements StockExchangeService {

    @Value("${settings.market-live-threshold}")
    private Integer marketLiveThreshold;

    private final StockService stockService;
    private final StockExchangeRepository stockExchangeRepository;

    @Override
    public StockExchange getStockExchangeByName(String name) {
        Optional<StockExchange> stockExchange = stockExchangeRepository.findByNameEqualsIgnoreCase(name);
        return stockExchange.orElseThrow(() -> new StockExchangeNotFoundException(name));
    }

    @Override
    @Transactional
    public StockExchange addStockToExchange(String exchangeName, String stockName) {
        return manageStockExchange(exchangeName, stockName, StockExchange::addStock);
    }

    @Override
    @Transactional
    public void deleteStockFromExchange(String exchangeName, String stockName) {
        manageStockExchange(exchangeName, stockName, StockExchange::removeStock);
    }

    private StockExchange manageStockExchange(
            String exchangeName,
            String stockName,
            BiConsumer<StockExchange, Stock> stockManager) {

        Stock stock = stockService.getByNameJoinExchangesAndLockRow(stockName);
        StockExchange stockExchange = getByNameJoinStocksAndLockRow(exchangeName);

        stockManager.accept(stockExchange, stock);
        stockExchange.setLiveInMarket(stockExchange.getStocks().size() >= marketLiveThreshold);

        return stockExchangeRepository.save(stockExchange);
    }

    private StockExchange getByNameJoinStocksAndLockRow(String name) {
        return stockExchangeRepository.getByNameEqualsIgnoreCase(name)
                .orElseThrow(() -> new StockExchangeNotFoundException(name));
    }
}
