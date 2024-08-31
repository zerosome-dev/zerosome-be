package com.zerosome.zerosomebe.domain.review.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zerosome.zerosomebe.domain.product.dto.ReviewThumbnail;
import com.zerosome.zerosomebe.domain.review.dto.ReviewDetailByMemberResponse;
import com.zerosome.zerosomebe.domain.review.dto.ReviewDetailResponse;
import com.zerosome.zerosomebe.domain.review.entity.Review;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {

    @Query("select new com.zerosome.zerosomebe.domain.product.dto.ReviewThumbnail("
           + "r.id, r.rating, r.contents, r.regDate) "
           + "FROM REVIEW r "
           + "WHERE r.productId = :productId AND r.showYn = true "
           + "ORDER BY r.regDate DESC")
    List<ReviewThumbnail> findThumbnailByProduct(Long productId, Pageable pageable);

    @Query("select new com.zerosome.zerosomebe.domain.review.dto.ReviewDetailResponse("
           + "r.id, r.rating, r.contents, r.regDate, m.nickname) "
           + "FROM REVIEW r "
           + "LEFT JOIN MEMBER m on r.memberId = m.id "
           + "WHERE r.productId = :productId AND r.showYn = true "
           + "ORDER BY r.regDate DESC")
    List<ReviewDetailResponse> findDetailByProductId(Long productId);

    @Query("select new com.zerosome.zerosomebe.domain.review.dto.ReviewDetailResponse("
           + "r.id, r.rating, r.contents, r.regDate, m.nickname) "
           + "FROM REVIEW r "
           + "LEFT JOIN MEMBER m on r.memberId = m.id "
           + "WHERE r.productId = :productId AND r.showYn = true "
           + "ORDER BY r.regDate DESC")
    List<ReviewDetailResponse> findDetailByProductId(Long productId, Pageable pageable);

    Long countAllByMemberIdAndShowYnTrue(Long memberId);

    Optional<Review> findFirstByIdAndShowYnIsTrue(Long reviewId);

    @Query("select new com.zerosome.zerosomebe.domain.review.dto.ReviewDetailByMemberResponse("
           + "r.id, r.rating, r.contents, c.codeDesc, p.name, p.image, r.regDate) "
           + "FROM REVIEW r "
           + "LEFT JOIN PRODUCT p ON r.productId = p.id "
           + "LEFT JOIN COMM_CODE c ON p.brandCode = c.code "
           + "WHERE r.memberId = :memberId AND r.showYn = true "
           + "ORDER BY r.regDate DESC")
    List<ReviewDetailByMemberResponse> findDetailByMemberId(Long memberId, Pageable pageable);
}
