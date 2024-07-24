package com.zerosome.zerosomebe.domain.product.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "카테고리 별 상품 목록 Request")
public class ProductByCtgListRequest {
    private OrderType orderType;
    private List<String> brandList;
    private List<String> zeroCtgList;

    public OrderType getOrderType() {
        return orderType;
    }

    public List<String> getBrandList() {
        return brandList;
    }

    public List<String> getZeroCtgList() {
        return zeroCtgList;
    }
}
