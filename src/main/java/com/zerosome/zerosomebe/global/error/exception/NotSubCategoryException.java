package com.zerosome.zerosomebe.global.error.exception;

import com.zerosome.zerosomebe.global.error.ErrorCode;

public class NotSubCategoryException extends BusinessException {

    public NotSubCategoryException() {
        super(ErrorCode.NOT_SUB_CATEGORY);
    }

    public NotSubCategoryException(String message) {
        super(message, ErrorCode.NOT_SUB_CATEGORY);
    }
}
