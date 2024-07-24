package com.zerosome.zerosomebe.domain.home.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class HomeBannerResponse {

    @NonNull Long id;
    @NonNull String image;
    @NonNull boolean externalYn;
    String url;
}
