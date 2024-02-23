package com.hh.mirishop.productmanagement.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // product 에러 관련
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "상품이 존재하지 않습니다."),

    // stock 에러 관련
    STOCK_NOT_FOUND(HttpStatus.NOT_FOUND, "수량있는 상품이 존재하지 않습니다."),
    STOCK_NOT_ENOUGH(HttpStatus.CONFLICT, "상품의 수량이 충분하지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
