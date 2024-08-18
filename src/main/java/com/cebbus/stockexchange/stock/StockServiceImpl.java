package com.cebbus.stockexchange.stock;

import com.cebbus.stockexchange.exception.StockNotFoundException;
import com.cebbus.stockexchange.exchange.StockExchange;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    @Value("${settings.market-live-threshold}")
    private Integer marketLiveThreshold;

    private final StockRepository stockRepository;

    @Override
    public Stock createStock(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public Stock updatePrice(Long id, UpdatePriceRequest request) {
        Stock stock = stockRepository.findById(id).orElseThrow(() -> new StockNotFoundException(id));
        stock.setCurrentPrice(request.getPrice());

        return stockRepository.save(stock);
    }

    @Override
    @Transactional
    public void deleteStock(Long id) {
        Stock stock = getByIdJoinExchangesAndLockRow(id);
        Set<StockExchange> stockExchanges = stock.getStockExchanges();
        stockExchanges.forEach(se -> se.removeStock(stock));

        stockExchanges.stream()
                .filter(se -> se.getStocks().size() < this.marketLiveThreshold)
                .forEach(se -> se.setLiveInMarket(false));

        stockRepository.delete(stock);
    }

    @Override
    @Transactional
    public Stock getByNameJoinExchangesAndLockRow(String name) {
        return stockRepository.findByNameEqualsIgnoreCase(name)
                .orElseThrow(() -> new StockNotFoundException(name));
    }

    public Stock getByIdJoinExchangesAndLockRow(Long id) {
        return stockRepository.getById(id)
                .orElseThrow(() -> new StockNotFoundException(id));
    }
}
