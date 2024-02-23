package com.hh.mirishop.productmanagement.stock.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StockRequest {

    private Long productId;
    private int quantity;
}
