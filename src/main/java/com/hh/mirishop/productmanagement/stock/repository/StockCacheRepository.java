package com.hh.mirishop.productmanagement.stock.repository;

import com.hh.mirishop.productmanagement.stock.entity.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class StockCacheRepository {

    private final RedisTemplate<String, Stock> stockRedisTemplate;

    public void setStock(Stock stock) {
        String key = "productId:" + stock.getProduct().getProductId();
        stockRedisTemplate.opsForValue().set(key, stock);
        log.info("redis stock설정, productId {} : {} ", key, stock);
    }

    public Optional<Stock> getStock(Long productId) {
        String key = "productId:" + productId;
        Stock stock = stockRedisTemplate.opsForValue().get(key);
        log.info("redis로 불러온 productId {} : {} ", key, stock);
        return Optional.ofNullable(stock);
    }
}
