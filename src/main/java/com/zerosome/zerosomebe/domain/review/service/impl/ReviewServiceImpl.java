package com.zerosome.zerosomebe.domain.review.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zerosome.zerosomebe.domain.member.repository.MemberJpaRepository;
import com.zerosome.zerosomebe.domain.review.dto.ReviewCreateRequest;
import com.zerosome.zerosomebe.domain.review.dto.ReviewDetailResponse;
import com.zerosome.zerosomebe.domain.review.entity.Review;
import com.zerosome.zerosomebe.domain.review.repository.ReviewJpaRepository;
import com.zerosome.zerosomebe.domain.review.service.ReviewService;
import com.zerosome.zerosomebe.global.error.exception.MemberNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {
    private final ReviewJpaRepository reviewJpaRepository;
    private final MemberJpaRepository memberJpaRepository;

    @Override
    public List<ReviewDetailResponse> getReviewDetailList(Long productId) {
        return reviewJpaRepository.findDetailByProductId(productId);
    }

    @Override
    @Transactional
    public void createReview(Long memberId, ReviewCreateRequest request) {
        Long productId = request.getProductId();
        int rating = request.getRating();
        String contents = request.getContents();

        if (!memberJpaRepository.existsById(memberId)) {
            throw new MemberNotFoundException();
        }

        Review review = new Review().of(productId, memberId, rating, contents, true);
        reviewJpaRepository.save(review);
    }
}
