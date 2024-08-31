package com.zerosome.zerosomebe.domain.review.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zerosome.zerosomebe.domain.review.dto.ReviewByMemberResponse;
import com.zerosome.zerosomebe.domain.review.dto.ReviewCreateRequest;
import com.zerosome.zerosomebe.domain.review.dto.ReviewDetailResponse;
import com.zerosome.zerosomebe.domain.review.dto.ReviewModifyRequest;
import com.zerosome.zerosomebe.domain.review.service.ReviewService;
import com.zerosome.zerosomebe.global.entity.ApiResponse;
import com.zerosome.zerosomebe.global.entity.OffsetPageResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/app/v1/review")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 목록 조회", description = "리뷰 목록 조회(일괄 조회)")
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<List<ReviewDetailResponse>>> getReviewDetailList(
            @RequestHeader(name = "Authorization", required = false) String accessToken,
            @Parameter(name = "productId", description = "상품 ID", in = ParameterIn.PATH)
            @PathVariable("productId") Long productId
    ) {
        List<ReviewDetailResponse> response = reviewService.getReviewDetailList(productId);
        return ApiResponse.ofSuccess(HttpStatus.OK, response);
    }

    @Operation(summary = "리뷰 목록 조회(pagination)", description = "페이지네이션이 적용된 리뷰 목록 조회 API")
    @GetMapping("/list/{productId}")
    public ResponseEntity<ApiResponse<OffsetPageResponse<List<ReviewDetailResponse>>>> getReviewDetailList(
            @RequestHeader(name = "Authorization", required = false) String accessToken,
            @Parameter(name = "productId", description = "상품 ID", in = ParameterIn.PATH)
            @PathVariable("productId") Long productId,
            @Parameter(name = "offset", description = "pagination page number", in = ParameterIn.QUERY)
            @RequestParam(value = "offset", required = false) Integer offset,
            @Parameter(name = "limit", description = "1번에 조회할 상품 개수(페이지당 상품 개수)", in = ParameterIn.QUERY)
            @RequestParam(value = "limit", required = false) Integer limit
    ) {
        OffsetPageResponse<List<ReviewDetailResponse>> response
                = reviewService.getReviewDetailList(productId, offset, limit);
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

    @Operation(summary = "리뷰 수정", description = "리뷰 수정")
    @PutMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> modifyReview(
            Principal principal,
            @Parameter(name = "reviewId", description = "리뷰 ID", in = ParameterIn.PATH)
            @PathVariable("reviewId") Long reviewId,
            @RequestBody ReviewModifyRequest request) {
        Long memberId = Long.parseLong(principal.getName());
        reviewService.modifyReview(memberId, reviewId, request);
        return ApiResponse.ofSuccessVoid(HttpStatus.CREATED);
    }

    @Operation(summary = "리뷰 삭제", description = "리뷰 삭제")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(
            Principal principal,
            @Parameter(name = "reviewId", description = "리뷰 ID", in = ParameterIn.PATH)
            @PathVariable("reviewId") Long reviewId
    ) {
        Long memberId = Long.parseLong(principal.getName());
        reviewService.deleteReview(memberId, reviewId);
        return ApiResponse.ofSuccessVoid(HttpStatus.CREATED);
    }

    @Operation(summary = "유저 작성 리뷰 목록 조회", description = "마이페이지 내 유저 작성 리뷰 목록 조회")
    @GetMapping("/member")
    public ResponseEntity<ApiResponse<OffsetPageResponse<ReviewByMemberResponse>>> getReviewByMember(
            Principal principal,
            @Parameter(name = "offset", description = "pagination page number", in = ParameterIn.QUERY)
            @RequestParam(value = "offset", required = false) Integer offset,
            @Parameter(name = "limit", description = "1번에 조회할 상품 개수(페이지당 상품 개수)", in = ParameterIn.QUERY)
            @RequestParam(value = "limit", required = false) Integer limit
    ) {
        Long memberId = Long.parseLong(principal.getName());
        OffsetPageResponse<ReviewByMemberResponse> response =
                reviewService.getReviewByMember(memberId, offset, limit);
        return ApiResponse.ofSuccess(HttpStatus.OK, response);
    }
}
