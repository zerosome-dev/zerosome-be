package com.zerosome.zerosomebe.domain.review.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class ReviewByMemberResponse {
    @NonNull Long memberId;
    @NonNull String nickname;
    @NonNull Long reviewCnt;
    @NonNull List<ReviewDetailByMemberResponse> reviewList;
}
