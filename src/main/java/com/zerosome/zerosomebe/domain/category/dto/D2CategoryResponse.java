package com.zerosome.zerosomebe.domain.category.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class D2CategoryResponse {
    @NonNull String d2CategoryCode;
    @NonNull String d2CategoryName;
    @NonNull String d2CategoryImage;
    @NonNull boolean noOptionYn;
}
