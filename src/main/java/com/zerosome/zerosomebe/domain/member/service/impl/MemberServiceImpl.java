package com.zerosome.zerosomebe.domain.member.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zerosome.zerosomebe.domain.member.dto.MemberBasicInfoResponse;
import com.zerosome.zerosomebe.domain.member.entity.Member;
import com.zerosome.zerosomebe.domain.member.repository.MemberJpaRepository;
import com.zerosome.zerosomebe.domain.member.service.MemberService;
import com.zerosome.zerosomebe.domain.review.repository.ReviewJpaRepository;
import com.zerosome.zerosomebe.global.error.exception.MemberNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    private final MemberJpaRepository memberJpaRepository;
    private final ReviewJpaRepository reviewJpaRepository;

    @Override
    public MemberBasicInfoResponse getMemberBasicInfo(Long memberId) {
        Member member = memberJpaRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Long reviewCnt = reviewJpaRepository.countAllByMemberIdAndShowYnTrue(memberId);
        return new MemberBasicInfoResponse(member.getNickname(), reviewCnt);
    }

    @Override
    @Transactional
    public void modifyNickname(Long memberId, String nickname) {
        Member member = memberJpaRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        member.updateNickname(nickname);
    }
}
