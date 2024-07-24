package com.zerosome.zerosomebe.domain.review.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDetailResponse {
    @NonNull Long reviewId;
    @NonNull int rating;
    String reviewContents;
    @NonNull String regDate;
    @NonNull String nickname;

    public ReviewDetailResponse(@NonNull Long reviewId, @NonNull int rating, String reviewContents,
                                @NonNull LocalDateTime regDate, @NonNull String nickname) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.reviewContents = reviewContents;
        this.regDate = regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.nickname = nickname;
    }
}
