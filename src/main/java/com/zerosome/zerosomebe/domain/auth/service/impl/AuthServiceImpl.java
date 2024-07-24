package com.zerosome.zerosomebe.domain.auth.service.impl;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zerosome.zerosomebe.domain.auth.dto.JoinRequest;
import com.zerosome.zerosomebe.domain.auth.dto.LoginResponse;
import com.zerosome.zerosomebe.domain.auth.dto.TokenResponse;
import com.zerosome.zerosomebe.domain.auth.service.AppleService;
import com.zerosome.zerosomebe.domain.auth.service.AuthService;
import com.zerosome.zerosomebe.domain.auth.service.KakaoService;
import com.zerosome.zerosomebe.domain.member.entity.Member;
import com.zerosome.zerosomebe.domain.member.entity.SocialType;
import com.zerosome.zerosomebe.domain.member.repository.MemberJpaRepository;
import com.zerosome.zerosomebe.global.auth.jwt.JwtTokenProvider;
import com.zerosome.zerosomebe.global.auth.jwt.UserAuthentication;
import com.zerosome.zerosomebe.global.error.exception.MemberNotFoundException;
import com.zerosome.zerosomebe.global.vo.Token;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {
    private static final int ACCESS_TOKEN_EXPIRATION = 7200000;
    private static final int REFRESH_TOKEN_EXPIRATION = 1209600000;

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberJpaRepository memberJpaRepository;
    private final AppleService appleService;
    private final KakaoService kakaoService;

    @Override
    public LoginResponse loginIfExist(String socialAccessToken, SocialType socialType)
            throws JsonProcessingException {
        Optional<Member> member = getMemberIfExists(socialAccessToken, socialType);
        if (member.isEmpty()) {
            return LoginResponse.ofNonMember();
        } else {
            Token token = getToken(member.get());
            return LoginResponse.ofMember(token);
        }
    }

    @Override
    public boolean validateNickname(String nickname) {
        return !memberJpaRepository.existsByNickname(nickname);
    }

    @Override
    @Transactional
    public TokenResponse signUp(String socialAccessToken, SocialType socialType, JoinRequest request)
            throws JsonProcessingException {
        Member member = getOrSaveMember(socialAccessToken, socialType, request);
        Token token = getToken(member);
        return TokenResponse.of(token);
    }

    @Override
    public TokenResponse refresh(Long memberId) {
        return TokenResponse.of(
                getToken(
                        findMember(memberId)));
    }

    @Transactional
    public void signOut(long memberId) {
        Member member = findMember(memberId);
        member.resetRefreshToken();
    }

    @Transactional
    public void withdraw(long memberId) {
        Member member = findMember(memberId);
        deleteMember(member);
    }

    private Optional<Member> getMemberIfExists(String socialAccessToken, SocialType socialType)
            throws JsonProcessingException {
        String socialId = getSocialId(socialAccessToken, socialType);
        return memberJpaRepository.findByProviderAndSocialId(socialType, socialId);
    }

    private Member getOrSaveMember(String socialAccessToken, SocialType socialType, JoinRequest request)
            throws JsonProcessingException {
        String socialId = getSocialId(socialAccessToken, socialType);
        return getOrSaveMemberData(socialType, socialId, request.getNickname(),
                                   request.isAgreement_marketing());
    }

    private String getSocialId(String socialAccessToken, SocialType socialType) throws JsonProcessingException {
        return switch (socialType) {
            case APPLE -> appleService.getAppleData(socialAccessToken);
            case KAKAO -> kakaoService.getKakaoData(socialAccessToken);
        };
    }

    private Member getOrSaveMemberData(SocialType socialType, String socialId, String nickname,
                                       boolean isAgreementMarketing) {
        return memberJpaRepository.findByProviderAndSocialId(socialType, socialId)
                                  .orElseGet(() -> saveMember(socialType, socialId, nickname,
                                                              isAgreementMarketing));
    }

    private Member saveMember(SocialType socialType, String socialId, String nickname,
                              boolean isAgreementMarketing) {
        Member member = Member.builder()
                              .socialType(socialType)
                              .socialId(socialId)
                              .nickname(nickname)
                              .agreeMarketing(isAgreementMarketing)
                              .build();
        return memberJpaRepository.save(member);
    }

    private Token getToken(Member member) {
        Token token = generateToken(new UserAuthentication(member.getId(), null, null));
        member.updateRefreshToken(token.getRefreshToken());
        return token;
    }

    private Token generateToken(Authentication authentication) {
        return Token.builder()
                    .accessToken(jwtTokenProvider.generateToken(authentication, ACCESS_TOKEN_EXPIRATION))
                    .refreshToken(jwtTokenProvider.generateToken(authentication, REFRESH_TOKEN_EXPIRATION))
                    .build();
    }

    private Member findMember(long id) {
        return memberJpaRepository.findById(id)
                                  .orElseThrow(MemberNotFoundException::new);
    }

    private void deleteMember(Member member) {
        memberJpaRepository.delete(member);
    }
}
