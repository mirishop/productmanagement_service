package com.hh.mirishop.productmanagement.service;

import com.hh.mirishop.productmanagement.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductQueryService {

    Page<ProductResponse> findProducts(Pageable pageable);

    ProductResponse find(long productId);
}
