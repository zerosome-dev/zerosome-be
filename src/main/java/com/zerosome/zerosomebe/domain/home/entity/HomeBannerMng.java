package com.zerosome.zerosomebe.domain.home.entity;

import com.zerosome.zerosomebe.global.entity.BaseTimeEntity;

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
@Entity(name = "HOME_BANNER_MNG")
public class HomeBannerMng extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MNG_HOME_SEQ", nullable = false)
    private Long id;

    @Column(name = "IMAGE", nullable = false)
    private String image;

    @Column(name = "PAGE_URL", nullable = false)
    private String pageUrl;

    @Column(name = "EXTERNAL_URL_YN", nullable = false)
    private boolean externalUrlYn;

    @Column(name = "ORDER_SEQ", nullable = false)
    private int orderSeq;

    @Column(name = "REG_MEMBER", nullable = false)
    private Long regMember;

    public HomeBannerMng(String image, String pageUrl, boolean externalUrlYn, int orderSeq, Long regMember) {
        this.image = image;
        this.pageUrl = pageUrl;
        this.externalUrlYn = externalUrlYn;
        this.orderSeq = orderSeq;
        this.regMember = regMember;
    }

    /*
        샘플 데이터

        INSERT INTO HOME_BANNER_MNG (MNG_HOME_SEQ, IMAGE, PAGE_URL, EXTERNAL_URL_YN, ORDER_SEQ,
        REG_MEMBER, REG_DATE)
        VALUES (1, 'banner/1.jpg', 'https://cloop.co.kr/product/detail.html?product_no=84', true, 1, 1, NOW());

        INSERT INTO HOME_BANNER_MNG (MNG_HOME_SEQ, IMAGE, PAGE_URL, EXTERNAL_URL_YN, ORDER_SEQ,
        REG_MEMBER, REG_DATE)
        VALUES (2, 'banner/2.png', 'zerosome://category', false, 2, 1, NOW());

    */
}
