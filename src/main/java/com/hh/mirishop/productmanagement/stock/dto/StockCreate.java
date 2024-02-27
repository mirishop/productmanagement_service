package com.hh.mirishop.productmanagement.stock.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StockCreate {

    private Long productId;
    private int quantity;
}
