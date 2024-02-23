package com.hh.mirishop.productmanagement.dto;

import com.hh.mirishop.productmanagement.entity.Product;
import lombok.Getter;

@Getter
public class ProductResponse {

    private Long productId;
    private String content;
    private Long price;
    private Integer quantity;

    public ProductResponse(Product product) {
        this.productId = product.getProductId();
        this.content = product.getContent();
        this.price = product.getPrice();
        this.quantity = product.getStock().getQuantity();
    }
}
