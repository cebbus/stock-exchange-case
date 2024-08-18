package com.cebbus.stockexchange.stock;

import com.cebbus.stockexchange.domain.Base;
import com.cebbus.stockexchange.exchange.StockExchange;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@ToString(of = "name", callSuper = true)
@EqualsAndHashCode(of = "name", callSuper = true)
@Entity
@Table(name = "stock", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class Stock extends Base {

    @Column(length = 50, nullable = false)
    @NotBlank(message = "Stock name is mandatory")
    @Size(min = 1, max = 50, message = "Stock name length must be between 1 and 50")
    private String name;

    @Column(length = 255, nullable = true)
    @Size(max = 255, message = "Stock description length must be less than or equal 255")
    private String description;

    @Column(columnDefinition = "NUMERIC(19,8)", precision = 19, scale = 8, nullable = false)
    @NotNull(message = "Stock price is mandatory")
    private BigDecimal currentPrice;

    @Column
    private LocalDateTime lastUpdate;

    @ManyToMany(mappedBy = "stocks")
    @JsonIgnore
    private Set<StockExchange> stockExchanges;

    @PrePersist
    public void onPrePersist() {
        this.lastUpdate = LocalDateTime.now();
    }

    @PreUpdate
    public void onPreUpdate() {
        this.lastUpdate = LocalDateTime.now();
    }
}
