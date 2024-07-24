package com.zerosome.zerosomebe.domain.home.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zerosome.zerosomebe.domain.home.dto.HomeBannerResponse;
import com.zerosome.zerosomebe.domain.home.dto.HomeCafeResponse;
import com.zerosome.zerosomebe.domain.home.dto.HomeRolloutResponse;
import com.zerosome.zerosomebe.domain.home.service.HomeService;
import com.zerosome.zerosomebe.global.entity.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/app/v1/home")
public class HomeController {

    private final HomeService homeService;

    @Operation(summary = "배너 영역 조회", description = "홈화면 내 배너 영역 조회")
    @GetMapping("/banner")
    public ResponseEntity<ApiResponse<List<HomeBannerResponse>>> getHomeBanner(
            @RequestHeader(name = "Authorization", required = false) String accessToken) {
        List<HomeBannerResponse> response = homeService.getHomeBanner();
        return ApiResponse.ofSuccess(HttpStatus.OK, response);
    }

    @Operation(summary = "출시 에정 신상품 영역", description = "홈화면 내 출시 에정 신상품 영역")
    @GetMapping("/rollout")
    public ResponseEntity<ApiResponse<List<HomeRolloutResponse>>> getHomeRollout(
            @RequestHeader(name = "Authorization", required = false) String accessToken) {
        List<HomeRolloutResponse> response = homeService.getHomeRollout();
        return ApiResponse.ofSuccess(HttpStatus.OK, response);
    }

    @Operation(summary = "카페 음료 영역", description = "홈화면 내 카페 음료 영역")
    @GetMapping("/cafe")
    public ResponseEntity<ApiResponse<List<HomeCafeResponse>>> getHomeCafe(
            @RequestHeader(name = "Authorization", required = false) String accessToken) {
        List<HomeCafeResponse> response = homeService.getHomeCafe();
        return ApiResponse.ofSuccess(HttpStatus.OK, response);
    }
}
