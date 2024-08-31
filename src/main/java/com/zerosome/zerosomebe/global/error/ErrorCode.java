package com.zerosome.zerosomebe.global.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "REQ00", "올바르지 않은 입력값입니다."),
    TOKEN_NOT_VALID(HttpStatus.UNAUTHORIZED, "AUTH00", "토큰이 유효하지 않습니다."),
    FORBIDDEN_INPUT_VALUE(HttpStatus.FORBIDDEN, "REQ00", "올바르지 않은 입력값입니다."),
    NOT_FOUND_API(HttpStatus.NOT_FOUND, "REQ00", "존재하지 않는 API입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", "존재하지 않는 사용자입니다."),
    MEMBER_NICKNAME_DUPLICATE(HttpStatus.BAD_REQUEST, "M002", "중복된 닉네임입니다."),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "존재하지 않는 상품입니다."),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "R001", "존재하지 않는 리뷰입니다."),
    NOT_REVIEW_OWNER(HttpStatus.FORBIDDEN, "R002", "리뷰 작성자가 아닙니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "C001", "존재하지 않는 카테고리입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "REQ02", "잘못된 HTTP 메서드를 호출했습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SYS00", "서버 에러가 발생했습니다."),
    SOCIAL_LOGIN_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "AUTH01", "소셜 로그인에 실패했습니다."),
    REST_CLIENT_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "REST01", "REST API 호출에 실패했습니다."),
    S3_SDK_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S3_SDK_ERROR", "S3 SDK 에러가 발생했습니다."),
    S3_SERVICE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S3_SERVICE_ERROR", "S3 서비스 에러가 발생했습니다.");

    private final String message;
    private final String code;
    private final HttpStatus status;

    ErrorCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}