package com.hh.mirishop.productmanagement.stock.service;

import com.hh.mirishop.productmanagement.common.exception.ErrorCode;
import com.hh.mirishop.productmanagement.common.exception.StockException;
import com.hh.mirishop.productmanagement.entity.Product;
import com.hh.mirishop.productmanagement.stock.entity.Stock;
import com.hh.mirishop.productmanagement.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    @Override
    @Transactional
    public void addStock(Product savedproduct, int quantity) {
        Stock stock = Stock.builder()
                .product(savedproduct)
                .quantity(quantity)
                .build();

        stockRepository.save(stock);
    }

    @Override
    @Transactional
    public void modifyStock(Long productId, int quantity) {
        Stock stock = stockRepository.findById(productId)
                .orElseThrow(() -> new StockException(ErrorCode.STOCK_NOT_FOUND));

        stock.update(quantity);
    }

    @Override
    @Transactional
    public void removeStock(Long productId, int quantity) {
        // Stock 엔티티 존재 확인
        Stock stock = stockRepository.findById(productId)
                .orElseThrow(() -> new StockException(ErrorCode.STOCK_NOT_FOUND));

        // 기존 재고에서 변경 재고를 차감
        int newQuantity = stock.getQuantity() - quantity;

        // 재고 수량 확인하여 0보다 작으면 에러
        if (newQuantity < 0) {
            throw new StockException(ErrorCode.STOCK_NOT_ENOUGH);
        }

        stock.update(newQuantity);
    }

    @Override
    @Transactional(readOnly = true)
    public Stock readStockCount(Long productId) {
        Optional<Stock> stockOptional = stockRepository.findById(productId);

        return stockOptional.orElseThrow(() -> new StockException(ErrorCode.STOCK_NOT_FOUND));
    }
}
