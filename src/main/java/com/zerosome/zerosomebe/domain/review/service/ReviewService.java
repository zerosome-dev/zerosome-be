package com.zerosome.zerosomebe.domain.review.service;

import java.util.List;

import com.zerosome.zerosomebe.domain.review.dto.ReviewByMemberResponse;
import com.zerosome.zerosomebe.domain.review.dto.ReviewCreateRequest;
import com.zerosome.zerosomebe.domain.review.dto.ReviewDetailResponse;
import com.zerosome.zerosomebe.domain.review.dto.ReviewModifyRequest;
import com.zerosome.zerosomebe.global.entity.OffsetPageResponse;

public interface ReviewService {
    List<ReviewDetailResponse> getReviewDetailList(Long productId);

    OffsetPageResponse<List<ReviewDetailResponse>> getReviewDetailList(Long productId, Integer offset,
                                                                       Integer limit);

    void createReview(Long memberId, ReviewCreateRequest request);

    void modifyReview(Long memberId, Long reviewId, ReviewModifyRequest request);

    void deleteReview(Long memberId, Long reviewId);

    OffsetPageResponse<ReviewByMemberResponse> getReviewByMember(Long memberId, Integer offset, Integer limit);
}
