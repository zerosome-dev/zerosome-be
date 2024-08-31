package com.zerosome.zerosomebe.domain.member.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zerosome.zerosomebe.domain.auth.service.AuthService;
import com.zerosome.zerosomebe.domain.member.dto.MemberBasicInfoResponse;
import com.zerosome.zerosomebe.domain.member.service.MemberService;
import com.zerosome.zerosomebe.global.entity.ApiResponse;
import com.zerosome.zerosomebe.global.error.exception.MemberNicknameDuplicateException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/app/v1/member")
public class MemberController {

    private final MemberService memberService;
    private final AuthService authService;

    @Operation(summary = "유저 기본 정보 조회", description = "마이페이지 내 유저 기본 정보 조회")
    @GetMapping()
    public ResponseEntity<ApiResponse<MemberBasicInfoResponse>> getMemberBasicInfo(
            Principal principal
    ) {
        Long memberId = Long.parseLong(principal.getName());
        MemberBasicInfoResponse response = memberService.getMemberBasicInfo(memberId);
        return ApiResponse.ofSuccess(HttpStatus.OK, response);
    }

    @Operation(summary = "닉네임 변경", description = "닉네임 변경 API")
    @PutMapping("/nickname")
    public ResponseEntity<ApiResponse<Void>> modifyNickname(
            Principal principal,
            @Parameter(name = "nickname", description = "확인할 닉네임", in = ParameterIn.QUERY)
            @RequestParam(value = "nickname", required = true) String nickname) {
        Long memberId = Long.parseLong(principal.getName());
        boolean isUnique = authService.validateNickname(nickname);
        if (isUnique) {
            memberService.modifyNickname(memberId, nickname);
            return ApiResponse.ofSuccessVoid(HttpStatus.CREATED);
        } else {
            throw new MemberNicknameDuplicateException();
        }
    }
}
