package com.zerosome.zerosomebe.domain.product.service;

import java.util.List;

import com.zerosome.zerosomebe.domain.product.dto.ProductByCtgListRequest;
import com.zerosome.zerosomebe.domain.product.dto.ProductByCtgResponse;
import com.zerosome.zerosomebe.global.entity.OffsetPageResponse;

public interface ProductByCtgService {
    OffsetPageResponse<List<ProductByCtgResponse>> getProductList(String d2categoryCode, Integer offset,
                                                                  Integer limit,
                                                                  ProductByCtgListRequest filter);
}
