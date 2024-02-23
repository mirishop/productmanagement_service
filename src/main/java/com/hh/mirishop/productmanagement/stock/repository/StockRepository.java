package com.hh.mirishop.productmanagement.stock.repository;

import com.hh.mirishop.productmanagement.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
