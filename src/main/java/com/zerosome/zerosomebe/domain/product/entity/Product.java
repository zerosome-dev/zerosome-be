package com.zerosome.zerosomebe.domain.product.entity;

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
@Entity(name = "PRODUCT")
public class Product extends ModTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRD_IDX", nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "BRAND", nullable = false)
    private String brandCode;

    @Column(name = "CATEGORY", nullable = false)
    private String categoryCode;

    @Column(name = "IMAGE", nullable = true)
    private String image;

    @Column(name = "SHOW_YN", nullable = false)
    private boolean showYn;

    @Column(name = "ROLLOUT_YN", nullable = false)
    private boolean rolloutYn;

    @Column(name = "REG_MEMBER", nullable = false)
    private Long regMember;

    @Column(name = "MOD_MEMBER", nullable = false)
    private Long modMember;

    public Product(String name, String brandCode, String categoryCode, String image, boolean showYn,
                   boolean rolloutYn, Long regMember) {
        this.name = name;
        this.brandCode = brandCode;
        this.categoryCode = categoryCode;
        this.image = image;
        this.showYn = showYn;
        this.rolloutYn = rolloutYn;
        this.regMember = regMember;
        this.modMember = regMember;
    }

    /*
       샘플 데이터

        INSERT INTO PRODUCT (PRD_IDX, NAME, BRAND, CATEGORY, IMAGE, SHOW_YN, ROLLOUT_YN, REG_MEMBER, MOD_MEMBER,
        REG_DATE, MOD_DATE)
        VALUES (1, '클룹 제로소다 레몬 250ml', 'BR3', 'EAT002', 'product/1.png', true, true, 1, 1, NOW(), NOW());

        INSERT INTO PRODUCT (PRD_IDX, NAME, BRAND, CATEGORY, IMAGE, SHOW_YN, ROLLOUT_YN, REG_MEMBER, MOD_MEMBER,
        REG_DATE, MOD_DATE)
        VALUES (2, '[클룹X나이스웨더] 제로소다 청사과', 'BR3', 'EAT002', 'product/2.jpg', true, true, 1, 1, NOW(), NOW());

        INSERT INTO PRODUCT (PRD_IDX, NAME, BRAND, CATEGORY, IMAGE, SHOW_YN, ROLLOUT_YN, REG_MEMBER, MOD_MEMBER,
        REG_DATE, MOD_DATE)
        VALUES (3, '델픽 우도땅콩새싹보리차', 'BR3', 'EAT004', 'product/3.jpg', true, false, 1, 1, NOW(), NOW());

     */
}
