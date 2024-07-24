package com.zerosome.zerosomebe.domain.filter.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zerosome.zerosomebe.domain.filter.dto.BrandFilterResponse;
import com.zerosome.zerosomebe.domain.filter.dto.D2CategoryFilterResponse;
import com.zerosome.zerosomebe.domain.filter.dto.ZeroCategoryFilterResponse;
import com.zerosome.zerosomebe.domain.filter.repository.FilterJpaRepository;
import com.zerosome.zerosomebe.domain.filter.service.FilterService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FilterServiceImpl implements FilterService {

    private final FilterJpaRepository filterRepository;

    @Override
    public List<D2CategoryFilterResponse> getSubCategoryList(String d1CategoryCode) {
        return filterRepository.findSubCategoryList(d1CategoryCode);
    }

    @Override
    public List<BrandFilterResponse> getBrandList() {
        return filterRepository.findBrandList();
    }

    @Override
    public List<ZeroCategoryFilterResponse> getZeroCategoryList() {
        return filterRepository.findZeroCategoryList();
    }
}
