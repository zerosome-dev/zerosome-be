package com.zerosome.zerosomebe.domain.review.entity;

import com.zerosome.zerosomebe.global.entity.ModTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "REVIEW")
public class Review extends ModTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RV_IDX", nullable = false)
    private Long id;

    @Column(name = "PRD_IDX", nullable = false)
    private Long productId;

    @Column(name = "MEMBER_IDX", nullable = false)
    private Long memberId;

    @Column(name = "RATING", nullable = false)
    private int rating;

    @Column(name = "CONTENTS", nullable = true)
    private String contents;

    @Column(name = "SHOW_YN", nullable = false)
    private boolean showYn;

    public Review of(Long productId, Long memberId, int rating, String contents, boolean showYn) {
        this.productId = productId;
        this.memberId = memberId;
        this.rating = rating;
        this.contents = contents;
        this.showYn = showYn;
        return this;
    }

    /*
       샘플 데이터

        INSERT INTO REVIEW (RV_IDX, PRD_IDX, MEMBER_IDX, RATING, CONTENTS, SHOW_YN, REG_DATE, MOD_DATE)
        VALUES (1, 1, 1, 5, '매우 좋아요.', true, NOW(), NOW());

        INSERT INTO REVIEW (RV_IDX, PRD_IDX, MEMBER_IDX, RATING, CONTENTS, SHOW_YN, REG_DATE, MOD_DATE)
        VALUES (2, 1, 1, 4, '좀 좋아요', true, NOW(), NOW());

        INSERT INTO REVIEW (RV_IDX, PRD_IDX, MEMBER_IDX, RATING, CONTENTS, SHOW_YN, REG_DATE, MOD_DATE)
        VALUES (3, 2, 1, 3, '보통이에요', true, NOW(), NOW());

     */
}
