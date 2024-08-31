package com.zerosome.zerosomebe.domain.member.service;

import com.zerosome.zerosomebe.domain.member.dto.MemberBasicInfoResponse;

public interface MemberService {
    MemberBasicInfoResponse getMemberBasicInfo(Long memberId);

    void modifyNickname(Long memberId, String nickname);
}
