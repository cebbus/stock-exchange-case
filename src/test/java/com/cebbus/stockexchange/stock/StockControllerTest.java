package com.cebbus.stockexchange.stock;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@WebMvcTest(StockController.class)
public class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService stockService;

    @Test
    void createStockShouldReturnCreatedStock() throws Exception {
        Stock testStock = new Stock();
        testStock.setId(1L);
        testStock.setName("Test");
        testStock.setCurrentPrice(BigDecimal.ZERO);

        Mockito.when(this.stockService.createStock(Mockito.any(Stock.class)))
                .thenReturn(testStock);

        String reqBody = new ObjectMapper().writeValueAsString(testStock);

        MockHttpServletRequestBuilder content = MockMvcRequestBuilders
                .post("/api/v1/stock")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .with(httpBasic("admin", "welcome"))
                .content(reqBody);

        this.mockMvc.perform(content)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(reqBody));

        Mockito.verify(this.stockService, Mockito.times(1))
                .createStock(Mockito.any(Stock.class));
    }

    @Test
    void updatePriceShouldReturnUpdatedStock() throws Exception {
        Stock testStock = new Stock();
        testStock.setId(1L);
        testStock.setName("Test");
        testStock.setCurrentPrice(BigDecimal.ZERO);

        UpdatePriceRequest request = new UpdatePriceRequest();
        request.setPrice(BigDecimal.ZERO);

        Mockito.when(this.stockService.updatePrice(1L, request))
                .thenReturn(testStock);

        String reqBody = new ObjectMapper().writeValueAsString(request);
        String respBody = new ObjectMapper().writeValueAsString(testStock);

        MockHttpServletRequestBuilder content = MockMvcRequestBuilders
                .patch("/api/v1/stock/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .with(httpBasic("admin", "welcome"))
                .content(reqBody);

        this.mockMvc.perform(content)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(respBody));

        Mockito.verify(this.stockService, Mockito.times(1))
                .updatePrice(1L, request);
    }

    @Test
    void deleteStockShouldNotReturnContent() throws Exception {
        Mockito.doNothing()
                .when(this.stockService)
                .deleteStock(Mockito.any(Long.class));

        MockHttpServletRequestBuilder content = MockMvcRequestBuilders
                .delete("/api/v1/stock/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .with(httpBasic("admin", "welcome"));

        this.mockMvc.perform(content)
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(stockService, Mockito.times(1))
                .deleteStock(1L);
    }
}
