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
public class LoginResponse {
    public static LoginResponse ofMember(Token token) {
        return new LoginResponse(true, TokenResponse.of(token));
    }

    public static LoginResponse ofNonMember() {
        return new LoginResponse(false, null);
    }

    @NonNull
    private Boolean isMember;
    private TokenResponse token;
}
