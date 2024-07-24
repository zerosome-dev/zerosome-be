package com.zerosome.zerosomebe.domain.filter.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zerosome.zerosomebe.domain.filter.dto.BrandFilterResponse;
import com.zerosome.zerosomebe.domain.filter.dto.D2CategoryFilterResponse;
import com.zerosome.zerosomebe.domain.filter.dto.ZeroCategoryFilterResponse;
import com.zerosome.zerosomebe.domain.filter.service.FilterService;
import com.zerosome.zerosomebe.global.entity.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/app/v1/filter")
public class FilterController {

    private final FilterService filterService;

    @Operation(summary = "하위 카테고리 필터 조회", description = "하위 카테고리 필터 조회")
    @GetMapping("/sub-category/{id}")
    public ResponseEntity<ApiResponse<List<D2CategoryFilterResponse>>> getSubCategoryList(
            @RequestHeader(name = "Authorization", required = false) String accessToken,
            @Parameter(name = "id", description = "상위 카테고리 코드(1 Depth)", in = ParameterIn.PATH)
            @PathVariable("id") String id
    ) {
        List<D2CategoryFilterResponse> response = filterService.getSubCategoryList(id);
        return ApiResponse.ofSuccess(HttpStatus.OK, response);
    }

    @Operation(summary = "브랜드 필터 조회", description = "브랜드 필터 조회")
    @GetMapping("/brand")
    public ResponseEntity<ApiResponse<List<BrandFilterResponse>>> getBrandList(
            @RequestHeader(name = "Authorization", required = false) String accessToken
    ) {
        List<BrandFilterResponse> response = filterService.getBrandList();
        return ApiResponse.ofSuccess(HttpStatus.OK, response);
    }

    @Operation(summary = "제로 카테고리 필터 조회", description = "제로 카테고리 필터 조회")
    @GetMapping("/zero-category")
    public ResponseEntity<ApiResponse<List<ZeroCategoryFilterResponse>>> getZeroCategoryList(
            @RequestHeader(name = "Authorization", required = false) String accessToken
    ) {
        List<ZeroCategoryFilterResponse> response = filterService.getZeroCategoryList();
        return ApiResponse.ofSuccess(HttpStatus.OK, response);
    }
}
