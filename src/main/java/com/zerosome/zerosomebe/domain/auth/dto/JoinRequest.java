package com.zerosome.zerosomebe.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@Schema(description = "회원가입 Request")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinRequest {
    @NonNull String nickname;
    @NonNull boolean agreement_marketing;
}
