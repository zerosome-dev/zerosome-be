package com.zerosome.zerosomebe.domain.product.service;

import com.zerosome.zerosomebe.domain.product.dto.ProductDetailResponse;

public interface ProductDetailService {
    ProductDetailResponse getProductDetail(Long productId);
}
