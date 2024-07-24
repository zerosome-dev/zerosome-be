package com.zerosome.zerosomebe.domain.category.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zerosome.zerosomebe.domain.category.dto.D1CategoryResponse;
import com.zerosome.zerosomebe.domain.category.service.CategoryService;
import com.zerosome.zerosomebe.global.entity.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/app/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 목록 조회", description = "카테고리 목록 조회")
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<D1CategoryResponse>>> getHomeBanner(
            @RequestHeader(name = "Authorization", required = false) String accessToken) {
        List<D1CategoryResponse> response = categoryService.getCategoryList();
        return ApiResponse.ofSuccess(HttpStatus.OK, response);
    }
}
