package com.zerosome.zerosomebe.domain.filter.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ZeroCategoryFilterResponse {
    @NonNull
    private String zeroCtgCode;
    @NonNull
    private String zeroCtgName;
}
