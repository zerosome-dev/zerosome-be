package com.zerosome.zerosomebe.domain.devutil.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zerosome.zerosomebe.domain.devutil.service.DevUtilService;
import com.zerosome.zerosomebe.domain.member.entity.Member;
import com.zerosome.zerosomebe.domain.member.repository.MemberJpaRepository;
import com.zerosome.zerosomebe.global.auth.jwt.JwtTokenProvider;
import com.zerosome.zerosomebe.global.auth.jwt.UserAuthentication;
import com.zerosome.zerosomebe.global.error.exception.MemberNotFoundException;
import com.zerosome.zerosomebe.global.vo.Token;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DevUtilServiceImpl implements DevUtilService {
    private static final int ACCESS_TOKEN_EXPIRATION = 7200000;
    private static final int REFRESH_TOKEN_EXPIRATION = 1209600000;
    private final MemberJpaRepository memberJpaRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public Token getTestMemberToken() {

        Member testMember = memberJpaRepository.findById(1L).orElseThrow(MemberNotFoundException::new);

        Authentication authentication = new UserAuthentication(testMember.getId(), null, null);

        Token token = Token.builder()
                           .accessToken(jwtTokenProvider.generateToken(authentication, ACCESS_TOKEN_EXPIRATION))
                           .refreshToken(
                                   jwtTokenProvider.generateToken(authentication, REFRESH_TOKEN_EXPIRATION))
                           .build();

        testMember.updateRefreshToken(token.getRefreshToken());

        return token;
    }
}
