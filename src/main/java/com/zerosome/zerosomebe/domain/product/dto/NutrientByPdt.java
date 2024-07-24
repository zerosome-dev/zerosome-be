package com.zerosome.zerosomebe.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class NutrientByPdt {
    @NonNull
    private String nutrientName;
    @NonNull
    private Double percentage;
    @NonNull
    private Double amount;
    @NonNull
    private String percentageUnit;
    @NonNull
    private String amountUnit;
}
