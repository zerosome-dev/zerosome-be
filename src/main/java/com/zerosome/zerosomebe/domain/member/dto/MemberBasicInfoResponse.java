package com.zerosome.zerosomebe.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberBasicInfoResponse {
    @NonNull String nickname;
    @NonNull Long reviewCnt;
}
