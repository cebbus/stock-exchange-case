package com.cebbus.stockexchange.exchange;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface StockExchangeRepository extends CrudRepository<StockExchange, Long> {

    @EntityGraph(attributePaths = {"stocks"})
    Optional<StockExchange> findByNameEqualsIgnoreCase(String name);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @EntityGraph(attributePaths = {"stocks"})
    Optional<StockExchange> getByNameEqualsIgnoreCase(String name);
}
