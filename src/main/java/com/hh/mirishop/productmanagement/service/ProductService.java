package com.hh.mirishop.productmanagement.service;

import com.hh.mirishop.productmanagement.dto.ProductCreate;
import com.hh.mirishop.productmanagement.dto.ProductUpdate;

public interface ProductService {

    Long create(ProductCreate productCreate);

    void update(Long productId, ProductUpdate productUpdate);

    void delete(Long productId);
}
