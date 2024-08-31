package com.zerosome.zerosomebe.domain.review.service.impl;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zerosome.zerosomebe.domain.member.entity.Member;
import com.zerosome.zerosomebe.domain.member.repository.MemberJpaRepository;
import com.zerosome.zerosomebe.domain.review.dto.ReviewByMemberResponse;
import com.zerosome.zerosomebe.domain.review.dto.ReviewCreateRequest;
import com.zerosome.zerosomebe.domain.review.dto.ReviewDetailByMemberResponse;
import com.zerosome.zerosomebe.domain.review.dto.ReviewDetailResponse;
import com.zerosome.zerosomebe.domain.review.dto.ReviewModifyRequest;
import com.zerosome.zerosomebe.domain.review.entity.Review;
import com.zerosome.zerosomebe.domain.review.repository.ReviewJpaRepository;
import com.zerosome.zerosomebe.domain.review.service.ReviewService;
import com.zerosome.zerosomebe.global.entity.OffsetPageResponse;
import com.zerosome.zerosomebe.global.error.exception.MemberNotFoundException;
import com.zerosome.zerosomebe.global.error.exception.NotReviewOwnerException;
import com.zerosome.zerosomebe.global.error.exception.ReviewNotFoundException;
import com.zerosome.zerosomebe.global.s3.S3Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {
    private final ReviewJpaRepository reviewJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final S3Service s3Service;

    @Override
    public List<ReviewDetailResponse> getReviewDetailList(Long productId) {
        return reviewJpaRepository.findDetailByProductId(productId);
    }

    @Override
    public OffsetPageResponse<List<ReviewDetailResponse>> getReviewDetailList(Long productId, Integer offset,
                                                                              Integer limit) {
        if (offset == null) {
            offset = 0;
        }
        if (limit == null) {
            limit = 10;
        }
        Pageable pageable = PageRequest.of(offset, limit);

        List<ReviewDetailResponse> response
                = reviewJpaRepository.findDetailByProductId(productId, pageable);

        return OffsetPageResponse.of(response, limit, offset + 1);
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

    @Override
    @Transactional
    public void modifyReview(Long memberId, Long reviewId, ReviewModifyRequest request) {
        if (!memberJpaRepository.existsById(memberId)) {
            throw new MemberNotFoundException();
        }

        Review review = reviewJpaRepository.findFirstByIdAndShowYnIsTrue(reviewId)
                                           .orElseThrow(ReviewNotFoundException::new);

        if (!review.getMemberId().equals(memberId)) {
            throw new NotReviewOwnerException();
        }

        if (request.isModifyContents()) {
            review.modifyContents(request.getContents());
        }

        if (request.getRating() != null) {
            review.modifyRating(request.getRating());
        }
    }

    @Override
    @Transactional
    public void deleteReview(Long memberId, Long reviewId) {
        if (!memberJpaRepository.existsById(memberId)) {
            throw new MemberNotFoundException();
        }

        Review review = reviewJpaRepository.findFirstByIdAndShowYnIsTrue(reviewId)
                                           .orElseThrow(ReviewNotFoundException::new);

        if (!review.getMemberId().equals(memberId)) {
            throw new NotReviewOwnerException();
        }

        review.setNotShow();
    }

    @Override
    public OffsetPageResponse<ReviewByMemberResponse> getReviewByMember(Long memberId, Integer offset,
                                                                        Integer limit) {
        Member member = memberJpaRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        if (offset == null) {
            offset = 0;
        }
        if (limit == null) {
            limit = 10;
        }
        Pageable pageable = PageRequest.of(offset, limit);

        List<ReviewDetailByMemberResponse> reviewList
                = reviewJpaRepository.findDetailByMemberId(memberId, pageable)
                                     .stream()
                                     .map(
                                             review -> review.setImage(
                                                     s3Service.generateGetPresignedUrl(
                                                             review.getProductImage())))
                                     .toList();

        Long reviewCnt = reviewJpaRepository.countAllByMemberIdAndShowYnTrue(memberId);
        ReviewByMemberResponse response
                = new ReviewByMemberResponse(memberId, member.getNickname(), reviewCnt, reviewList);

        return OffsetPageResponse.of(response, limit, offset + 1);
    }
}
