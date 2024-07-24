package com.zerosome.zerosomebe.domain.product.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class ProductDetailResponse {
    @NonNull
    private Long productId;
    private String image;
    @NonNull
    private String brandName;
    @NonNull
    private String productName;
    @NonNull
    private List<NutrientByPdt> nutrientList;
    @NonNull
    private List<OfflineStore> offlineStoreList;
    @NonNull
    private List<OnlineStore> onlineStoreList;
    private Double rating;
    @NonNull
    private Long reviewCnt;
    @NonNull
    private List<ReviewThumbnail> reviewThumbnailList;
    @NonNull
    private List<SimilarProduct> similarProductList;

    public ProductDetailResponse(ProductDetail productDetail, List<NutrientByPdt> nutrientList,
                                 List<OfflineStore> offlineStoreList, List<OnlineStore> onlineStoreList,
                                 List<ReviewThumbnail> reviewThumbnailList,
                                 List<SimilarProduct> similarProductList) {
        this.productId = productDetail.getProductId();
        this.image = productDetail.getImage();
        this.brandName = productDetail.getBrandName();
        this.productName = productDetail.getProductName();
        this.nutrientList = nutrientList;
        this.offlineStoreList = offlineStoreList;
        this.onlineStoreList = onlineStoreList;
        this.rating = productDetail.getRating();
        this.reviewCnt = productDetail.getReviewCnt();
        this.reviewThumbnailList = reviewThumbnailList;
        this.similarProductList = similarProductList;
    }
}
