package com.zerosome.zerosomebe.domain.home.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HomeCafeResponse {
    @NonNull Long id;
    @NonNull String image;
    @NonNull String d1CategoryId;
    @NonNull String d2CategoryId;
    @NonNull String name;
    @NonNull String brand;
    Double review;
    @NonNull Long reviewCnt;
}
