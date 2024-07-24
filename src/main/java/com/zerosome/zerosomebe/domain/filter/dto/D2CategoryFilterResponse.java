package com.zerosome.zerosomebe.domain.filter.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class D2CategoryFilterResponse {
    @NonNull
    private String d2CategoryCode;
    @NonNull
    private String d2CategoryName;
    @NonNull
    private boolean noOptionYn;
}
