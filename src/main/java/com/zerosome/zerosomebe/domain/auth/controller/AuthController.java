package com.zerosome.zerosomebe.domain.auth.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zerosome.zerosomebe.domain.auth.dto.JoinRequest;
import com.zerosome.zerosomebe.domain.auth.dto.LoginResponse;
import com.zerosome.zerosomebe.domain.auth.dto.TokenResponse;
import com.zerosome.zerosomebe.domain.auth.service.AuthService;
import com.zerosome.zerosomebe.domain.member.entity.SocialType;
import com.zerosome.zerosomebe.global.entity.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "로그인", description = "로그인(카카오톡/애플)")
    @PostMapping
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @RequestHeader("Authorization") String socialAccessToken,
            @Parameter(name = "socialType", description = "소셜 로그인 타입", in = ParameterIn.QUERY)
            @RequestParam(value = "socialType", required = true) SocialType socialType)
            throws JsonProcessingException {
        socialAccessToken = socialAccessToken.substring("Bearer ".length());
        System.out.println("socialAccessToken = " + socialAccessToken);
        LoginResponse response = authService.loginIfExist(socialAccessToken, socialType);
        return ApiResponse.ofSuccess(HttpStatus.OK, response);
    }

    @Operation(summary = "닉네임 중복 확인", description = "닉네임 중복 확인 API")
    @GetMapping("/nickname")
    public ResponseEntity<ApiResponse<Boolean>> validateNickname(
            @Parameter(name = "nickname", description = "확인할 닉네임", in = ParameterIn.QUERY)
            @RequestParam(value = "nickname", required = true) String nickname) {
        boolean response = authService.validateNickname(nickname);

        return ApiResponse.ofSuccess(HttpStatus.OK, response);
    }

    @Operation(summary = "access token 갱신", description = "access token 갱신")
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenResponse>> refresh(Principal principal) {
        Long memberId = Long.parseLong(principal.getName());
        TokenResponse response = authService.refresh(memberId);
        return ApiResponse.ofSuccess(HttpStatus.CREATED, response);
    }

    @Operation(summary = "회원가입", description = "회원가입(카카오톡/애플)")
    @PostMapping("/join")
    public ResponseEntity<ApiResponse<TokenResponse>> join(
            @RequestHeader("Authorization") String socialAccessToken,
            @Parameter(name = "socialType", description = "소셜 로그인 타입", in = ParameterIn.QUERY)
            @RequestParam(value = "socialType", required = true) SocialType socialType,
            @RequestBody JoinRequest request) throws JsonProcessingException {
        socialAccessToken = socialAccessToken.substring("Bearer ".length());
        System.out.println("socialAccessToken = " + socialAccessToken);
        TokenResponse response = authService.signUp(socialAccessToken, socialType, request);
        return ApiResponse.ofSuccess(HttpStatus.CREATED, response);
    }

    @Operation(summary = "로그아웃", description = "")
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> signOut(Principal principal) {
        long memberId = Long.parseLong(principal.getName());
        authService.signOut(memberId);
        return ApiResponse.ofSuccessVoid(HttpStatus.OK);
    }

    @Operation(summary = "", description = "회원가입(카카오톡/애플)")
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> withdrawal(Principal principal) {
        long memberId = Long.parseLong(principal.getName());
        authService.withdraw(memberId);
        return ApiResponse.ofSuccessVoid(HttpStatus.OK);
    }
}
