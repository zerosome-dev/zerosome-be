package com.zerosome.zerosomebe.domain.home.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class HomeRolloutResponse {
    @NonNull Long id;
    @NonNull String image;
    @NonNull String d1Category;
    @NonNull String d2Category;
    @NonNull String name;
    List<String> salesStore;
}
