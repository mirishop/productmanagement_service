package com.hh.mirishop.productmanagement.product.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProductUpdate {

    private String name;
    private String content;
    private Long price;
    private Integer quantity;
    private LocalDateTime reservationTime;
}