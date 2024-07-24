package com.zerosome.zerosomebe.domain.home.service;

import java.util.List;

import com.zerosome.zerosomebe.domain.home.dto.HomeBannerResponse;
import com.zerosome.zerosomebe.domain.home.dto.HomeCafeResponse;
import com.zerosome.zerosomebe.domain.home.dto.HomeRolloutResponse;

public interface HomeService {
    List<HomeBannerResponse> getHomeBanner();

    List<HomeRolloutResponse> getHomeRollout();

    List<HomeCafeResponse> getHomeCafe();
}
