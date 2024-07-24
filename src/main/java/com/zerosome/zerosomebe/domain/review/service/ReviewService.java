package com.zerosome.zerosomebe.domain.review.service;

import java.util.List;

import com.zerosome.zerosomebe.domain.review.dto.ReviewCreateRequest;
import com.zerosome.zerosomebe.domain.review.dto.ReviewDetailResponse;

public interface ReviewService {
    List<ReviewDetailResponse> getReviewDetailList(Long productId);

    void createReview(Long memberId, ReviewCreateRequest request);
}
