package com.cebbus.stockexchange.exchange;

import com.cebbus.stockexchange.domain.Base;
import com.cebbus.stockexchange.stock.Stock;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Data
@ToString(of = "name", callSuper = true)
@EqualsAndHashCode(of = "name", callSuper = true)
@Entity
@Table(name = "stock_exchange", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class StockExchange extends Base {

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 255, nullable = true)
    private String description;

    @Column
    private boolean liveInMarket;

    @ManyToMany
    @JoinTable(
            name = "stock_exchange_stock_mapping",
            joinColumns = @JoinColumn(name = "exchange_id"),
            inverseJoinColumns = @JoinColumn(name = "stock_id"))
    private Set<Stock> stocks;

    public void addStock(Stock stock) {
        this.stocks.add(stock);
        stock.getStockExchanges().add(this);
    }

    public void removeStock(Stock stock) {
        this.stocks.remove(stock);
    }
}
