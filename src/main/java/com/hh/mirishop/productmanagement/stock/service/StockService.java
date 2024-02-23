package com.hh.mirishop.productmanagement.stock.service;

import com.hh.mirishop.productmanagement.entity.Product;
import com.hh.mirishop.productmanagement.stock.entity.Stock;

public interface StockService {

    void addStock(Product savedproduct, int quantity);

    void modifyStock(Long productId, int quantity);

    void removeStock(Long productId, int quantity);

    Stock readStockCount(Long productId);
}
