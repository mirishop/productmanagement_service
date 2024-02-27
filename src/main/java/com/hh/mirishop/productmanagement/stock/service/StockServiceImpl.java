package com.hh.mirishop.productmanagement.stock.service;

import com.hh.mirishop.productmanagement.common.exception.ErrorCode;
import com.hh.mirishop.productmanagement.common.exception.StockException;
import com.hh.mirishop.productmanagement.product.entity.Product;
import com.hh.mirishop.productmanagement.stock.entity.Stock;
import com.hh.mirishop.productmanagement.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final ConcurrentHashMap<Long, ReentrantLock> locks = new ConcurrentHashMap<>();

    @Override
    @Transactional
    @CachePut(value = "stock", key = "#root.args[0].productId") // 파라미터 값을 받아오지 못하여 args 인자로 전달
    public Integer addStock(Product savedProduct, int quantity) {
        Stock stock = Stock.builder()
                .product(savedProduct)
                .quantity(quantity)
                .build();

        stockRepository.save(stock);

        return stock.getQuantity();
    }

    @Override
    @Transactional
    @CachePut(value = "stock", key = "#root.args[0]")
    public void modifyStock(Long productId, int quantity) {
        Stock stock = stockRepository.findById(productId)
                .orElseThrow(() -> new StockException(ErrorCode.STOCK_NOT_FOUND));

        stock.update(quantity);
    }

    @Override
    @Transactional
    @CachePut(value = "stock", key = "#root.args[0]")
    public void decreaseStock(Long productId, int count) {
        log.info("Decreasing stock for productId: {}, count: {}", productId, count);

        Stock stock = stockRepository.findById(productId)
                .orElseThrow(() -> new StockException(ErrorCode.STOCK_NOT_FOUND));

        int newQuantity = stock.getQuantity() - count;
        if (newQuantity < 0) {
            log.warn("Not enough stock for productId: {}. Requested: {}, Available: {}", productId, count, stock.getQuantity());
            throw new StockException(ErrorCode.STOCK_NOT_ENOUGH);
        }

        stock.update(newQuantity);
        log.info("Stock decreased for productId: {}. New quantity: {}", productId, newQuantity);
    }

    @Override
    @Transactional
    @CachePut(value = "stock", key = "#root.args[0]")
    public void restoreStock(Long productId, int count) {
        ReentrantLock lock = locks.computeIfAbsent(productId, k -> new ReentrantLock());
        boolean isLocked = false;

        try {
            isLocked = lock.tryLock();

            if (!isLocked) {
                throw new StockException(ErrorCode.STOCK_LOCK_FAILURE);
            }

            // Stock 엔티티 존재 확인
            Stock stock = stockRepository.findById(productId)
                    .orElseThrow(() -> new StockException(ErrorCode.STOCK_NOT_FOUND));

            // 기존 재고에서 count만큼 복구
            int newQuantity = stock.getQuantity() + count;

            // 재고 수량 확인하여 0보다 작으면 에러
            if (newQuantity < 0) {
                throw new StockException(ErrorCode.STOCK_NOT_ENOUGH);
            }

            stock.update(newQuantity);
        } finally {
            if (isLocked) {
                lock.unlock();
            }
        }
    }
}
