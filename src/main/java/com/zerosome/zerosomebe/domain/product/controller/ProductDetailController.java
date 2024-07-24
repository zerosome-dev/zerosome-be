package com.zerosome.zerosomebe.domain.product.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zerosome.zerosomebe.domain.product.dto.ProductDetailResponse;
import com.zerosome.zerosomebe.domain.product.service.ProductDetailService;
import com.zerosome.zerosomebe.global.entity.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/app/v1/product/detail")
public class ProductDetailController {

    private final ProductDetailService productDetailService;

    @Operation(summary = "상품 상세 정보", description = "상품 상세 정보")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDetailResponse>> getProductDetail(
            @RequestHeader(name = "Authorization", required = false) String accessToken,
            @Parameter(name = "id", description = "상품 ID", in = ParameterIn.PATH)
            @PathVariable("id") Long id
    ) {
        ProductDetailResponse response = productDetailService.getProductDetail(id);
        return ApiResponse.ofSuccess(HttpStatus.OK, response);
    }
}
