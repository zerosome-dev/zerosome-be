package com.zerosome.zerosomebe.domain.category.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zerosome.zerosomebe.domain.category.dto.AllFoodCategory;
import com.zerosome.zerosomebe.domain.category.dto.D1CategoryResponse;
import com.zerosome.zerosomebe.domain.category.dto.D2CategoryResponse;
import com.zerosome.zerosomebe.domain.category.repository.CategoryJpaRepository;
import com.zerosome.zerosomebe.domain.category.service.CategoryService;
import com.zerosome.zerosomebe.global.s3.S3Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final S3Service s3Service;
    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public List<D1CategoryResponse> getCategoryList() {
        return buildCategoryList(categoryJpaRepository.findAllFoodCategoryList());
    }

    private List<D1CategoryResponse> buildCategoryList(List<AllFoodCategory> categoryList) {
        List<D1CategoryResponse> rtn = new ArrayList<>();

        D1CategoryResponse d1Category = null;
        List<D2CategoryResponse> d2Categories = new ArrayList<>();

        for (AllFoodCategory category : categoryList) {
            if (d1Category == null || !d1Category.getD1CategoryCode().equals(category.getD1CategoryCode())) {
                if (d1Category != null) {
                    d1Category.setD2Category(d2Categories);
                    rtn.add(d1Category);
                }
                d1Category = D1CategoryResponse.builder()
                                               .d1CategoryCode(category.getD1CategoryCode())
                                               .d1CategoryName(category.getD1CategoryName())
                                               .d2Category(new ArrayList<>())
                                               .build();
                d2Categories = new ArrayList<>();
            }

            d2Categories.add(D2CategoryResponse.builder()
                                               .d2CategoryCode(category.getD2CategoryCode())
                                               .d2CategoryName(category.getD2CategoryName())
                                               .d2CategoryImage(s3Service.generateGetPresignedUrl(
                                                       category.getD2CategoryImage()))
                                               .noOptionYn(category.getNoOptionYn())
                                               .build());
        }

        if (categoryList.size() > 0) {
            d1Category.setD2Category(d2Categories);
            rtn.add(d1Category);
        }

        return rtn;
    }
}
