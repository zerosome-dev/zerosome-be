package com.zerosome.zerosomebe.domain.review.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zerosome.zerosomebe.domain.review.dto.ReviewCreateRequest;
import com.zerosome.zerosomebe.domain.review.dto.ReviewDetailResponse;
import com.zerosome.zerosomebe.domain.review.service.ReviewService;
import com.zerosome.zerosomebe.global.entity.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/app/v1/review")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 목록 조회", description = "리뷰 목록 조회")
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<List<ReviewDetailResponse>>> getReviewDetailList(
            @RequestHeader(name = "Authorization", required = false) String accessToken,
            @Parameter(name = "productId", description = "상품 ID", in = ParameterIn.PATH)
            @PathVariable("productId") Long productId
    ) {
        List<ReviewDetailResponse> response = reviewService.getReviewDetailList(productId);
        return ApiResponse.ofSuccess(HttpStatus.OK, response);
    }

    @Operation(summary = "리뷰 등록", description = "리뷰 등록")
    @PostMapping
    public ResponseEntity<ApiResponse<List<ReviewDetailResponse>>> createReview(
            Principal principal, @RequestBody ReviewCreateRequest request) {
        Long memberId = Long.parseLong(principal.getName());
        reviewService.createReview(memberId, request);
        return ApiResponse.ofSuccessVoid(HttpStatus.CREATED);
    }
}
