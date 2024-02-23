package com.hh.mirishop.productmanagement.service;

import com.hh.mirishop.productmanagement.common.exception.ErrorCode;
import com.hh.mirishop.productmanagement.common.exception.ProductException;
import com.hh.mirishop.productmanagement.dto.ProductResponse;
import com.hh.mirishop.productmanagement.entity.Product;
import com.hh.mirishop.productmanagement.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductRepository productRepository;

    /**
     * 전체 상품 조회
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> findProducts(Pageable pageable) {
        Page<Product> products = productRepository.findByIsDeletedFalse(pageable);

        return products.map(ProductResponse::new);
    }


    /**
     * 상품 한 개 조회
     */
    @Override
    @Transactional
    public ProductResponse find(long productId) {
        Product product = productRepository.findByProductIdAndIsDeletedFalse(productId)
                .orElseThrow(() -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND));

        return new ProductResponse(product);
    }
}