package com.cebbus.stockexchange.stock;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class UpdatePriceRequest {
    @NotNull(message = "Stock price is mandatory")
    private BigDecimal price;
}
