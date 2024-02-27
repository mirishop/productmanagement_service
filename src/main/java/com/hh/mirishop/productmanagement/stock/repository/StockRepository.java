package com.hh.mirishop.productmanagement.stock.repository;

import com.hh.mirishop.productmanagement.stock.entity.Stock;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

//    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT) // 낙관적 락 우선 적용
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Stock> findById(Long id);
}
