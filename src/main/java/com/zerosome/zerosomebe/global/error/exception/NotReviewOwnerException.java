package com.zerosome.zerosomebe.global.error.exception;

import com.zerosome.zerosomebe.global.error.ErrorCode;

public class NotReviewOwnerException extends BusinessException {

    public NotReviewOwnerException() {super(ErrorCode.NOT_REVIEW_OWNER);}

    public NotReviewOwnerException(String message) {super(message, ErrorCode.NOT_REVIEW_OWNER);}
}
