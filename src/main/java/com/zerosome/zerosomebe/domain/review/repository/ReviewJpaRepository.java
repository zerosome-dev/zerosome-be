package com.zerosome.zerosomebe.domain.review.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zerosome.zerosomebe.domain.product.dto.ReviewThumbnail;
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
}
