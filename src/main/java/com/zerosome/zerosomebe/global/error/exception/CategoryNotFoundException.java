package com.zerosome.zerosomebe.global.error.exception;

import com.zerosome.zerosomebe.global.error.ErrorCode;

public class CategoryNotFoundException extends BusinessException {

    public CategoryNotFoundException() {
        super(ErrorCode.CATEGORY_NOT_FOUND);
    }

    public CategoryNotFoundException(String message) {
        super(message, ErrorCode.CATEGORY_NOT_FOUND);
    }
}
