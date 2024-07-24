package com.zerosome.zerosomebe.domain.code.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "CODE_GROUP")
public class CodeGroup {
    @Id
    @Column(name = "CODE", nullable = false)
    private String groupCode;

    @Column(name = "CODE_DESC", nullable = false)
    private String codeDesc;

    /*
        샘플 데이터

        INSERT INTO CODE_GROUP (CODE, CODE_DESC) VALUES ('BRAND', '브랜드 그룹');
        INSERT INTO CODE_GROUP (CODE, CODE_DESC) VALUES ('CTG', '식품 카테고리 그룹');
        INSERT INTO CODE_GROUP (CODE, CODE_DESC) VALUES ('NUTRIENT', '영양 성분 그룹');
        INSERT INTO CODE_GROUP (CODE, CODE_DESC) VALUES ('ZEROTAG', '제로 카테고리 그룹');
        INSERT INTO CODE_GROUP (CODE, CODE_DESC) VALUES ('STORE', '판매처 그룹');

     */
}
