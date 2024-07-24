package com.zerosome.zerosomebe.domain.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "NUTRIENT")
public class Nutrient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NUT_IDX", nullable = false)
    private Long id;

    @Column(name = "PRD_IDX", nullable = false)
    private Long productId;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "NUTRIENT_CODE", nullable = true)
    private String nutrientCode;

    @Column(name = "AMOUNT", nullable = false)
    private double amount;

    @Column(name = "AMOUNT_UNIT", nullable = false)
    private String amountUnit;

    @Column(name = "PERCENTAGE", nullable = false)
    private double percentage;

    @Column(name = "PERCENTAGE_UNIT", nullable = false)
    private String percentageUnit;

    @Builder
    public Nutrient(Long productId, String name, String nutrientCode, double amount, String amountUnit,
                    double percentage, String percentageUnit) {
        this.productId = productId;
        this.name = name;
        this.nutrientCode = nutrientCode;
        this.amount = amount;
        this.amountUnit = amountUnit;
        this.percentage = percentage;
        this.percentageUnit = percentageUnit;
    }


    /*
       샘플 데이터

       INSERT INTO NUTRIENT (NUT_IDX, PRD_IDX, NAME, NUTRIENT_CODE, AMOUNT, AMOUNT_UNIT, PERCENTAGE, PERCENTAGE_UNIT)
       VALUES (1, 1, '기준 용량', 'NUT001', 20, 'kcal', 500, 'ml');

       INSERT INTO NUTRIENT (NUT_IDX, PRD_IDX, NAME, NUTRIENT_CODE, AMOUNT, AMOUNT_UNIT, PERCENTAGE, PERCENTAGE_UNIT)
       VALUES (1, 1, '나트륨', 'NUT002', 55, 'mg', 3, '%');

       INSERT INTO NUTRIENT (NUT_IDX, PRD_IDX, NAME, NUTRIENT_CODE, AMOUNT, AMOUNT_UNIT, PERCENTAGE, PERCENTAGE_UNIT)
       VALUES (1, 1, '탄수화물', 'NUT003', 18, 'g', 6, '%');

     */
}
