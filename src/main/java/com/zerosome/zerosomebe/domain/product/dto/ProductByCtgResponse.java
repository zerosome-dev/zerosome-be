package com.zerosome.zerosomebe.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductByCtgResponse {
    @NonNull Long productId;
    String image;
    @NonNull String brandName;
    @NonNull String productName;
    Double rating;
    @NonNull Long reviewCnt;

    public ProductByCtgResponse setImage(String image) {
        this.image = image;
        return this;
    }
}
