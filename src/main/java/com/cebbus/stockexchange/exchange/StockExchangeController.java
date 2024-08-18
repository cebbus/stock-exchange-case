package com.cebbus.stockexchange.exchange;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stock-exchange")
public class StockExchangeController {

    private final StockExchangeService stockExchangeService;

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public StockExchange getStockExchange(@PathVariable String name) {
        return stockExchangeService.getStockExchangeByName(name);
    }

    @PutMapping("/{name}/stocks/{stockName}")
    @ResponseStatus(HttpStatus.OK)
    public StockExchange addStockToExchange(
            @PathVariable String name,
            @PathVariable String stockName) {
        return stockExchangeService.addStockToExchange(name, stockName);
    }

    @DeleteMapping("/{name}/stocks/{stockName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStockFromExchange(
            @PathVariable String name,
            @PathVariable String stockName) {
        stockExchangeService.deleteStockFromExchange(name, stockName);
    }

}
