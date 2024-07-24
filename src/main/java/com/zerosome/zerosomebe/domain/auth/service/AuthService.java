package com.zerosome.zerosomebe.domain.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zerosome.zerosomebe.domain.auth.dto.JoinRequest;
import com.zerosome.zerosomebe.domain.auth.dto.LoginResponse;
import com.zerosome.zerosomebe.domain.auth.dto.TokenResponse;
import com.zerosome.zerosomebe.domain.member.entity.SocialType;

public interface AuthService {
    LoginResponse loginIfExist(String socialAccessToken, SocialType socialType) throws JsonProcessingException;

    boolean validateNickname(String nickname);

    TokenResponse signUp(String socialAccessToken, SocialType socialType, JoinRequest request)
            throws JsonProcessingException;

    TokenResponse refresh(Long memberId);

    void signOut(long memberId);

    void withdraw(long memberId);
}
