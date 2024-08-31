package com.zerosome.zerosomebe.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@Schema(description = "리뷰 등록 Request")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewModifyRequest {
    String contents;
    @NonNull boolean modifyContents;
    Integer rating;
}
