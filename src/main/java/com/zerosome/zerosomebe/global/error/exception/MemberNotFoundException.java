package com.zerosome.zerosomebe.global.error.exception;

import com.zerosome.zerosomebe.global.error.ErrorCode;

public class MemberNotFoundException extends BusinessException {

    public MemberNotFoundException() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }

    public MemberNotFoundException(String message) {
        super(message, ErrorCode.MEMBER_NOT_FOUND);
    }
}
