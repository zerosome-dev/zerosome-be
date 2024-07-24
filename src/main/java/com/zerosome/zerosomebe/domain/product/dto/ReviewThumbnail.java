package com.zerosome.zerosomebe.domain.product.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
public class ReviewThumbnail {
    @NonNull
    private Long reviewId;
    @NonNull
    private int rating;
    private String reviewContents;
    @NonNull
    private String regDate;

    public ReviewThumbnail(@NonNull Long reviewId, @NonNull int rating, String reviewContents,
                           @NonNull LocalDateTime regDate) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.reviewContents = reviewContents;
        this.regDate = regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
