package com.hh.mirishop.productmanagement.stock.controller;

import com.hh.mirishop.productmanagement.common.dto.BaseResponse;
import com.hh.mirishop.productmanagement.stock.service.StockQueryService;
import com.hh.mirishop.productmanagement.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/internal/stocks")
public class InternalStockController {

    private final StockService stockService;
    private final StockQueryService stockQueryService;

    @GetMapping("/{productId}")
    public ResponseEntity<BaseResponse<Integer>> readStock(@PathVariable("productId") Long productId) {
        Integer stockCount = stockQueryService.readStockCount(productId);
        return ResponseEntity.ok(new BaseResponse<>("상품 재고 조회 완료", true, stockCount));
    }

    @PostMapping("/decrease")
    public ResponseEntity<BaseResponse<Void>> decreaseStock(@RequestParam("productId") Long productId,
                                                            @RequestParam("count") int count) {
        stockService.decreaseStock(productId, count);
        return ResponseEntity.ok(new BaseResponse<>("구매 재고 차감 완료", true, null));
    }

    @PostMapping("/restore")
    public ResponseEntity<BaseResponse<Void>> restoreStock(@RequestParam("productId") Long productId,
                                                           @RequestParam("count") int count) {
        stockService.restoreStock(productId, count);
        return ResponseEntity.ok(new BaseResponse<>("구매 취소 재고 복원 완료", true, null));
    }

}
