package com.zerosome.zerosomebe.global.entity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.zerosome.zerosomebe.global.error.ErrorCode;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(description = "API Response")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    public static <T> ResponseEntity<ApiResponse<T>> ofSuccessVoid(HttpStatus httpStatus) {
        ApiResponse<T> response = new ApiResponse<>();

        response.code = "SUCCESS";
        response.status = true;
        response.data = null;

        return ResponseEntity.status(httpStatus).body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> ofSuccess(HttpStatus httpStatus, T data) {
        ApiResponse<T> response = new ApiResponse<>();

        response.code = "SUCCESS";
        response.data = data;
        response.status = true;

        return ResponseEntity.status(httpStatus).body(response);
    }

    public static <T> ResponseEntity<Object> ofError(ErrorCode code) {
        ApiResponse response = new ApiResponse();

        response.code = code.getCode();
        response.status = false;
        response.data = null;

        return ResponseEntity.status(code.getStatus()).body(response);
    }

    public static <T> ResponseEntity<Object> ofError(ErrorCode code, final String message) {
        /* TODO : Message Logging */
        ApiResponse response = new ApiResponse();

        response.code = code.getCode();
        response.status = false;
        response.data = null;

        return ResponseEntity.status(code.getStatus()).body(response);
    }

    @Schema(description = "응답 코드. 성공시 'SUCCESS', 에러시 에러코드 표출")
    String code;
    @Schema(description = "성공 여부")
    boolean status;
    @Schema(description = "API 응답 데이터")
    T data;

}
