package com.hh.mirishop.productmanagement.stock.service;

import com.hh.mirishop.productmanagement.common.exception.ErrorCode;
import com.hh.mirishop.productmanagement.common.exception.StockException;
import com.hh.mirishop.productmanagement.stock.entity.Stock;
import com.hh.mirishop.productmanagement.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StockQueryServiceImpl implements StockQueryService {

    private final StockRepository stockRepository;

    @Override
    @Transactional(readOnly = true)
    public Stock readStock(Long productId) {
        return stockRepository.findById(productId)
                .orElseThrow(() -> new StockException(ErrorCode.STOCK_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "stock", key = "#root.args[0]")
    public Integer readStockCount(Long productId) {
        Stock stock = stockRepository.findById(productId)
                .orElseThrow(() -> new StockException(ErrorCode.STOCK_NOT_FOUND));

        return stock.getQuantity();
    }
}
