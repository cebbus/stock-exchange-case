package com.cebbus.stockexchange.stock;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface StockRepository extends CrudRepository<Stock, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @EntityGraph(attributePaths = {"stockExchanges", "stockExchanges.stocks"})
    Optional<Stock> getById(Long id);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @EntityGraph(attributePaths = {"stockExchanges"})
    Optional<Stock> findByNameEqualsIgnoreCase(String name);
}
