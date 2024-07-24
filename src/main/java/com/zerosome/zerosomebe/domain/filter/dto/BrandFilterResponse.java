package com.zerosome.zerosomebe.domain.filter.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BrandFilterResponse {
    @NonNull
    private String brandCode;
    @NonNull
    private String brandName;
}
