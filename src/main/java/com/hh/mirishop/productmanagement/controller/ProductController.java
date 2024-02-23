package com.hh.mirishop.productmanagement.controller;

import com.hh.mirishop.productmanagement.common.dto.dto.BaseResponse;
import com.hh.mirishop.productmanagement.dto.ProductCreate;
import com.hh.mirishop.productmanagement.dto.ProductResponse;
import com.hh.mirishop.productmanagement.dto.ProductUpdate;
import com.hh.mirishop.productmanagement.service.ProductQueryService;
import com.hh.mirishop.productmanagement.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final ProductQueryService productQueryService;

    /**
     * 상품 전체 조회
     */
    @GetMapping
    public ResponseEntity<BaseResponse<Page<ProductResponse>>> readAll(Pageable pageable) {
        Page<ProductResponse> products = productQueryService.findProducts(pageable);
        return ResponseEntity.ok(new BaseResponse<>("상품 전체 조회 성공", true, products));
    }

    /**
     * 상품 단건 조회
     */
    @GetMapping("/{productId}")
    public ResponseEntity<BaseResponse<ProductResponse>> read(@PathVariable("productId") Long productId) {
        ProductResponse productResponse = productQueryService.find(productId);
        return ResponseEntity.ok(new BaseResponse<>("상품 조회 성공", true, productResponse));
    }

    /**
     * 상품 추가 메소드
     */
    @PostMapping
    public ResponseEntity<BaseResponse<Void>> create(@RequestBody ProductCreate productCreate) {
        productService.create(productCreate);

        return ResponseEntity.ok(new BaseResponse<>("상품 생성 성공", true, null));
    }

    /**
     * 상품 수정 메소드
     */
    @PutMapping("/{productId}")
    public ResponseEntity<BaseResponse<Void>> update(@PathVariable("productId") Long productId,
                                                     @RequestBody ProductUpdate productUpdate) {
        productService.update(productId, productUpdate);

        return ResponseEntity.ok(new BaseResponse<>("상품이 업데이트되었습니다.", true, null));
    }

    /**
     * 상품 삭제 메소드
     */
    @PostMapping
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable("productId") Long productId) {
        productService.delete(productId);

        return ResponseEntity.ok(new BaseResponse<>("상품이 삭제되었습니다.", true, null));
    }
}
