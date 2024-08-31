package com.zerosome.zerosomebe.domain.wish.service;

import com.zerosome.zerosomebe.domain.review.dto.ReviewCreateRequest;

public interface WishService {
    void createWish(Long memberId, ReviewCreateRequest request);

    void deleteWish(Long memberId, ReviewCreateRequest request);
}
