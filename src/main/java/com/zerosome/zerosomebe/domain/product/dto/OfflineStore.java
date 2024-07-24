package com.zerosome.zerosomebe.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class OfflineStore {
    private String storeCode;
    @NonNull
    private String storeName;
}
