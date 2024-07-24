package com.zerosome.zerosomebe.global.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.zerosome.zerosomebe.global.entity.ApiResponse;
import com.zerosome.zerosomebe.global.error.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException e) {
        log.error("handleBusinessException", e);
        return handleExceptionInternal(e.getErrorCode());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException e) {
        log.warn("handleIllegalArgument", e);
        return handleExceptionInternal(ErrorCode.INVALID_INPUT_VALUE, e.getMessage());
    }

    // @Valid 예외처리
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.warn("handleIllegalArgument", e);
        return handleExceptionInternal(ErrorCode.INVALID_INPUT_VALUE);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.warn("handleHttpMessageNotReadable", e);
        return handleExceptionInternal(ErrorCode.INVALID_INPUT_VALUE);
    }

    // 이 외의 500 에러 처리
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAllException(Exception ex) {
        log.error("handleAllException", ex);
        return handleExceptionInternal(ErrorCode.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(SdkClientException.class)
//    public ResponseEntity<ApiResponse> handleR2SdkClientException(SdkClientException e) {
//        log.error("S3SdkClientException", e);
//        return handleExceptionInternal(ErrorCode.S3_SDK_ERROR);
//    }

//    @ExceptionHandler(S3Exception.class)
//    public ResponseEntity<ApiResponse> handleR2Exception(S3Exception e) {
//        log.error("R2Exception", e);
//        return handleExceptionInternal(ErrorCode.S3_SERVICE_ERROR);
//    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<Object> handleRestClientException(RestClientException e) {
        log.error("RestClientException", e);
        return ApiResponse.ofError(ErrorCode.REST_CLIENT_FAILED);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ApiResponse.ofError(errorCode);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, String message) {
        return ApiResponse.ofError(errorCode, message);
    }
}
