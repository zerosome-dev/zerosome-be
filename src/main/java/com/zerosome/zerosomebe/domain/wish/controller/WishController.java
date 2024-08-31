package com.zerosome.zerosomebe.domain.wish.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zerosome.zerosomebe.domain.review.service.ReviewService;
import com.zerosome.zerosomebe.domain.wish.service.WishService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/app/v1/wish")
public class WishController {

    private final WishService wishService;
    private final ReviewService reviewService;

    /*
    @Operation(summary = "찜 목록 조회", description = "페이지네이션이 적용된 찜 목록 조회 API")
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<OffsetPageResponse<List<WishProductResponse>>>> getWishProductList(
            Principal principal,
            @Parameter(name = "offset", description = "pagination page number", in = ParameterIn.QUERY)
            @RequestParam(value = "offset", required = false) Integer offset,
            @Parameter(name = "limit", description = "1번에 조회할 상품 개수(페이지당 상품 개수)", in = ParameterIn.QUERY)
            @RequestParam(value = "limit", required = false) Integer limit
    ) {
        Long memberId = Long.parseLong(principal.getName());
        OffsetPageResponse<List<WishProductResponse>> response
                = wishService.getWishProductList(memberId, offset, limit);
        return ApiResponse.ofSuccess(HttpStatus.OK, response);
    }

    @Operation(summary = "찜 등록", description = "찜 등록")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createWish(
            Principal principal, @RequestBody ReviewCreateRequest request) {
        Long memberId = Long.parseLong(principal.getName());
        wishService.createWish(memberId, request);
        return ApiResponse.ofSuccessVoid(HttpStatus.CREATED);
    }

    @Operation(summary = "찜 취소", description = "찜 취소")
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteWish(
            Principal principal, @RequestBody ReviewCreateRequest request) {
        Long memberId = Long.parseLong(principal.getName());
        wishService.deleteWish(memberId, request);
        return ApiResponse.ofSuccessVoid(HttpStatus.CREATED);
    }
    */
}
