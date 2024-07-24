package com.zerosome.zerosomebe.global.error.exception;

import com.zerosome.zerosomebe.global.error.ErrorCode;

public class MemberNicknameDuplicateException extends BusinessException {

    public MemberNicknameDuplicateException() {
        super(ErrorCode.MEMBER_NICKNAME_DUPLICATE);
    }

    public MemberNicknameDuplicateException(String message) {
        super(message, ErrorCode.MEMBER_NICKNAME_DUPLICATE);
    }
}
