package com.zerosome.zerosomebe.domain.product.dto;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class SimilarProduct {
    /*
        카테고리(1&2Depth)가 동일하면서, 현재 보고 있는 상품이 아닌 상품을 무작위로 10개 노출
        연관상품이 없는 경우, 해당 블록 미노출
     */
    @NonNull
    private Long productId;
    private String image;
    @NonNull
    private String brandName;
    @NonNull
    private String productName;
    private Double rating;
    @NonNull
    private Long reviewCnt;

    public SimilarProduct(SimilarProductInterface product, String s3ImageUrl) {
        this.productId = product.getproductId();
        this.image = s3ImageUrl;
        this.brandName = product.getbrandName();
        this.productName = product.getproductName();
        this.rating = product.getrating();
        this.reviewCnt = product.getreviewCnt();
    }
}
