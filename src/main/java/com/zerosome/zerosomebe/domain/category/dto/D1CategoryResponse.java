package com.zerosome.zerosomebe.domain.category.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class D1CategoryResponse {
    @NonNull String d1CategoryCode;
    @NonNull String d1CategoryName;
    @NonNull List<D2CategoryResponse> d2Category;

    public void setD2Category(List<D2CategoryResponse> d2CategoryList) {
        this.d2Category = d2CategoryList;
    }
}
