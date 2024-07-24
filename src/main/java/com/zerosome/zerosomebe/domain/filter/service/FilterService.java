package com.zerosome.zerosomebe.domain.filter.service;

import java.util.List;

import com.zerosome.zerosomebe.domain.filter.dto.BrandFilterResponse;
import com.zerosome.zerosomebe.domain.filter.dto.D2CategoryFilterResponse;
import com.zerosome.zerosomebe.domain.filter.dto.ZeroCategoryFilterResponse;

public interface FilterService {
    List<D2CategoryFilterResponse> getSubCategoryList(String d1CategoryCode);

    List<BrandFilterResponse> getBrandList();

    List<ZeroCategoryFilterResponse> getZeroCategoryList();
}
