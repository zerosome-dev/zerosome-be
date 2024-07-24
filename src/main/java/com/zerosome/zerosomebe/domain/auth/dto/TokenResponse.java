package com.zerosome.zerosomebe.domain.auth.dto;

import com.zerosome.zerosomebe.global.vo.Token;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenResponse {
    public static TokenResponse of(Token token) {
        return new TokenResponse(token.getAccessToken(), token.getRefreshToken());
    }

    private @NonNull String accessToken;
    private @NonNull String refreshToken;
}
