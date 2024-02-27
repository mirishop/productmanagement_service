package com.hh.mirishop.productmanagement.product.controller;

import com.hh.mirishop.productmanagement.product.dto.ProductResponse;
import com.hh.mirishop.productmanagement.product.service.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/internal/products")
public class InternalProductController {

    private final ProductQueryService productQueryService;

    @GetMapping("/{productId}")
    public ProductResponse read(@PathVariable("productId") Long productId) {
        return productQueryService.find(productId);
    }
}
