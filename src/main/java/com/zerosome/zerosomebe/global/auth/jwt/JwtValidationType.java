package com.zerosome.zerosomebe.global.auth.jwt;

/*
 * JWT 토큰에 대한 에러 ENUM
 */
public enum JwtValidationType {
    VALID_JWT,
    INVALID_JWT_SIGNATURE,
    INVALID_JWT_TOKEN,
    EXPIRED_JWT_TOKEN,
    UNSUPPORTED_JWT_TOKEN,
    EMPTY_JWT
}