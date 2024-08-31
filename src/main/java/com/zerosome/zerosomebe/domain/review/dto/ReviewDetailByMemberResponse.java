package com.zerosome.zerosomebe.domain.review.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class ReviewDetailByMemberResponse {
    @NonNull Long reviewId;
    @NonNull int rating;
    String contents;
    @NonNull String brandName;
    @NonNull String productName;
    String productImage;
    @NonNull String regDate;

    public ReviewDetailByMemberResponse(@NonNull Long reviewId, @NonNull int rating, String contents,
                                        @NonNull String brandName, @NonNull String productName,
                                        String productImage, @NonNull LocalDateTime regDate) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.contents = contents;
        this.brandName = brandName;
        this.productName = productName;
        this.productImage = productImage;
        this.regDate = regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public ReviewDetailByMemberResponse setImage(String productImage) {
        this.productImage = productImage;
        return this;
    }
}
