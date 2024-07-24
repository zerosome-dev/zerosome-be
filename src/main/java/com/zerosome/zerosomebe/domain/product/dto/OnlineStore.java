package com.zerosome.zerosomebe.domain.product.dto;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class OnlineStore extends OfflineStore {
    private String url;

    public OnlineStore(String storeCode, @NonNull String storeName, String url) {
        super(storeCode, storeName);
        this.url = url;
    }
}
