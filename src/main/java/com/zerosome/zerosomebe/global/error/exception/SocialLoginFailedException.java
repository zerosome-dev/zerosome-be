package com.zerosome.zerosomebe.global.error.exception;

import com.zerosome.zerosomebe.global.error.ErrorCode;

public class SocialLoginFailedException extends BusinessException {

    public SocialLoginFailedException() {
        super(ErrorCode.SOCIAL_LOGIN_FAILED);
    }

    public SocialLoginFailedException(String message) {
        super(message, ErrorCode.SOCIAL_LOGIN_FAILED);
    }
}
