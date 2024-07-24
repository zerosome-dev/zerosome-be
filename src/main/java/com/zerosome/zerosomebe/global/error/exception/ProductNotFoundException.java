package com.zerosome.zerosomebe.global.error.exception;

import com.zerosome.zerosomebe.global.error.ErrorCode;

public class ProductNotFoundException extends BusinessException {

    public ProductNotFoundException() {
        super(ErrorCode.PRODUCT_NOT_FOUND);
    }

    public ProductNotFoundException(String message) {
        super(message, ErrorCode.MEMBER_NOT_FOUND);
    }
}
