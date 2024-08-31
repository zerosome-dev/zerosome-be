package com.zerosome.zerosomebe.global.error.exception;

import com.zerosome.zerosomebe.global.error.ErrorCode;

public class ReviewNotFoundException extends BusinessException {

    public ReviewNotFoundException() {super(ErrorCode.REVIEW_NOT_FOUND);}

    public ReviewNotFoundException(String message) {
        super(message, ErrorCode.REVIEW_NOT_FOUND);
    }
}
