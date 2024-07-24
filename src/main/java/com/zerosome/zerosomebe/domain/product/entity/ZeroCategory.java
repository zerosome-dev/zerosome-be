package com.zerosome.zerosomebe.domain.product.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ZERO_CATEGORY")
public class ZeroCategory {
    @Id
    @EmbeddedId
    private ZeroCategoryPK id;

    /*
       샘플 데이터

        INSERT INTO NUTRIENT (NUT_IDX, PRD_IDX, NAME, NUTRIENT_CODE, AMOUNT, SERVING_PERCENT, AMOUNT_STANDARD)
        VALUES (1, 1, '기준 용량', 'NUT001', 100, 10, 'kcal');

        INSERT INTO NUTRIENT (NUT_IDX, PRD_IDX, NAME, NUTRIENT_CODE, AMOUNT, SERVING_PERCENT, AMOUNT_STANDARD)
        VALUES (1, 1, '나트륨', 'NUT002', 30, 5.5, '%');

     */
}
