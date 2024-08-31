package com.zerosome.zerosomebe.domain.product.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zerosome.zerosomebe.domain.product.dto.ProductByCtgListRequest;
import com.zerosome.zerosomebe.domain.product.dto.ProductByCtgResponse;
import com.zerosome.zerosomebe.domain.product.service.ProductByCtgService;
import com.zerosome.zerosomebe.global.entity.ApiResponse;
import com.zerosome.zerosomebe.global.entity.OffsetPageResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/app/v1/product/category")
public class ProductByCtgController {

    private final ProductByCtgService productByCtgService;

    @Operation(summary = "하위 카테고리별 상품 목록 조회", description = "주어진 하위(2 Depth) 카테고리 내 상품 목록 조회")
    @PostMapping("/{d2categoryCode}")
    public ResponseEntity<ApiResponse<OffsetPageResponse<List<ProductByCtgResponse>>>> getProductList(
            @RequestHeader(name = "Authorization", required = false) String accessToken,
            @Parameter(name = "d2categoryCode", description = "하위 카테고리 코드(2 Depth)", in = ParameterIn.PATH)
            @PathVariable("d2categoryCode") String d2categoryCode,
            @Parameter(name = "offset", description = "pagination page number", in = ParameterIn.QUERY)
            @RequestParam(value = "offset", required = false) Integer offset,
            @Parameter(name = "limit", description = "1번에 조회할 상품 개수(페이지당 상품 개수)", in = ParameterIn.QUERY)
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestBody(required = false) ProductByCtgListRequest filter) {
        OffsetPageResponse<List<ProductByCtgResponse>> response = productByCtgService.getProductList(
                d2categoryCode, offset, limit, filter);
        return ApiResponse.ofSuccess(HttpStatus.OK, response);
    }
}
