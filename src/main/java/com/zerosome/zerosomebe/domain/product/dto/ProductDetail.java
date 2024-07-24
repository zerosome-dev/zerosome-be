package com.zerosome.zerosomebe.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class ProductDetail {
    @NonNull
    private Long productId;
    private String image;
    @NonNull
    private String brandName;
    @NonNull
    private String productName;
    @NonNull
    private String categoryCode;
    private Double rating;
    @NonNull
    private Long reviewCnt;

    public ProductDetail setImage(String image) {
        this.image = image;
        return this;
    }
}
