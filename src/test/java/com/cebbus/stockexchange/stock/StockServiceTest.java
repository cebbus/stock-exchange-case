package com.cebbus.stockexchange.stock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {

    @InjectMocks
    private StockServiceImpl stockService;

    @Mock
    private StockRepository stockRepository;

    private Stock stock;

    @BeforeEach
    public void setup() {
        this.stock = new Stock();
        this.stock.setId(1L);
        this.stock.setName("Test");
        this.stock.setCurrentPrice(BigDecimal.ZERO);
        this.stock.setStockExchanges(new HashSet<>());
    }

    @Test
    void createStockShouldReturnCreatedStock() throws Exception {
        when(this.stockRepository.save(any(Stock.class))).thenReturn(this.stock);

        Stock savedStock = this.stockService.createStock(this.stock);

        assertEquals(this.stock, savedStock);
        verify(this.stockRepository, times(1)).save(any(Stock.class));
    }

    @Test
    void updatePriceShouldReturnUpdatedStock() throws Exception {
        when(this.stockRepository.findById(1L)).thenReturn(Optional.of(this.stock));
        when(this.stockRepository.save(this.stock)).thenReturn(this.stock);

        UpdatePriceRequest request = new UpdatePriceRequest();
        request.setPrice(BigDecimal.ZERO);

        Stock updatedStock = this.stockService.updatePrice(1L, request);

        assertEquals(this.stock, updatedStock);
        verify(this.stockRepository, times(1)).findById(1L);
        verify(this.stockRepository, times(1)).save(this.stock);
    }

    @Test
    void deleteStockShouldNotReturnContent() throws Exception {
        when(this.stockRepository.getById(1L)).thenReturn(Optional.of(this.stock));
        doNothing().when(this.stockRepository).delete(this.stock);

        this.stockService.deleteStock(1L);

        verify(this.stockRepository, times(1)).getById(1L);
        verify(this.stockRepository, times(1)).delete(this.stock);
    }

}
