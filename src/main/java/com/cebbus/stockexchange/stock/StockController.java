package com.cebbus.stockexchange.stock;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stock")
public class StockController {

    private final StockService stockService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Stock createStock(@Valid @RequestBody Stock stock) {
        return stockService.createStock(stock);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Stock updatePrice(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePriceRequest request) {
        return stockService.updatePrice(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
    }

}
