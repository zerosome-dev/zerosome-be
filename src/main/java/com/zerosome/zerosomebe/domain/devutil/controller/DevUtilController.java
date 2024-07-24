package com.zerosome.zerosomebe.domain.devutil.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zerosome.zerosomebe.domain.auth.dto.TokenResponse;
import com.zerosome.zerosomebe.domain.devutil.service.DevUtilService;
import com.zerosome.zerosomebe.global.entity.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dev-util")
public class DevUtilController {

    private final DevUtilService devUtilService;

    @Operation(summary = "테스트용 토큰 발급", description = "유저 ID = 1 인 테스트용 토큰 발급")
    @GetMapping
    public ResponseEntity<ApiResponse<TokenResponse>> getTestMemberToken() {
        TokenResponse response = TokenResponse.of(devUtilService.getTestMemberToken());
        return ApiResponse.ofSuccess(HttpStatus.OK, response);
    }

    /*@Operation(summary = "리뷰 등록", description = "리뷰 등록")
    @PostMapping("/{productId}")
    public ResponseEntity<ApiResponse<List<ReviewDetailResponse>>> createReview(
            @RequestHeader(name = "Authorization", required = true) String accessToken,
            @Parameter(name = "productId", description = "상품 ID", in = ParameterIn.PATH)
            @PathVariable("productId") Long productId,
            @RequestBody ReviewCreateRequest request
    ) {
        reviewService.createReview(productId);
        return ApiResponse.ofSuccessVoid(HttpStatus.CREATED);
    }*/
}
