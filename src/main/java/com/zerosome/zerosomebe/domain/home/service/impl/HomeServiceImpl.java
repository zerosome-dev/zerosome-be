package com.zerosome.zerosomebe.domain.home.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zerosome.zerosomebe.domain.home.dto.HomeBannerResponse;
import com.zerosome.zerosomebe.domain.home.dto.HomeCafeResponse;
import com.zerosome.zerosomebe.domain.home.dto.HomeRolloutResponse;
import com.zerosome.zerosomebe.domain.home.dto.RolloutProduct;
import com.zerosome.zerosomebe.domain.home.dto.RolloutProductStore;
import com.zerosome.zerosomebe.domain.home.repository.HomeBannerMngJpaRepository;
import com.zerosome.zerosomebe.domain.home.repository.HomeProductJpaRepository;
import com.zerosome.zerosomebe.domain.home.service.HomeService;
import com.zerosome.zerosomebe.domain.product.repository.SalesStoreJpaRepository;
import com.zerosome.zerosomebe.global.s3.S3Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeServiceImpl implements HomeService {
    private final S3Service s3Service;
    private final HomeBannerMngJpaRepository bannerMngJpaRepository;
    private final HomeProductJpaRepository homeProductJpaRepository;
    private final SalesStoreJpaRepository storeJpaRepository;

    @Override
    public List<HomeBannerResponse> getHomeBanner() {
        return bannerMngJpaRepository.findAllBanners()
                                     .stream()
                                     .map(banner ->
                                                  HomeBannerResponse.builder()
                                                                    .id(banner.getId())
                                                                    .image(s3Service.generateGetPresignedUrl(
                                                                            banner.getImage()))
                                                                    .externalYn(banner.isExternalUrlYn())
                                                                    .url(banner.getPageUrl())
                                                                    .build())
                                     .toList();
    }

    @Override
    public List<HomeRolloutResponse> getHomeRollout() {
        List<RolloutProduct> productList = homeProductJpaRepository.findRolloutProductList();
        List<Long> productIdList = productList.stream()
                                              .map(RolloutProduct::getId)
                                              .toList();
        Map<Long, List<String>> storeList = storeJpaRepository.findRolloutProductStoreList(productIdList)
                                                              .stream()
                                                              .collect(
                                                                      Collectors.groupingBy(
                                                                              RolloutProductStore::getProductId,
                                                                              Collectors.mapping(
                                                                                      RolloutProductStore::getStoreName,
                                                                                      Collectors.toList())));

        return productList.stream()
                          .map(product ->
                                       HomeRolloutResponse.builder()
                                                          .id(product.getId())
                                                          .image(s3Service.generateGetPresignedUrl(
                                                                  product.getImage()))
                                                          .d1Category(product.getD1Category())
                                                          .d2Category(product.getD2Category())
                                                          .name(product.getName())
                                                          .salesStore(storeList.get(product.getId()))
                                                          .build()).toList();
    }

    @Override
    public List<HomeCafeResponse> getHomeCafe() {
        return homeProductJpaRepository.findCafeProductList()
                                       .stream()
                                       .map(product ->
                                                    HomeCafeResponse.builder()
                                                                    .id(product.getId())
                                                                    .image(s3Service.generateGetPresignedUrl(
                                                                            product.getImage()))
                                                                    .d1CategoryId(product.getD1CategoryId())
                                                                    .d2CategoryId(product.getD2CategoryId())
                                                                    .name(product.getName())
                                                                    .brand(product.getBrand())
                                                                    .review(product.getReview())
                                                                    .reviewCnt(product.getReviewCnt())
                                                                    .build())
                                       .toList();
    }
}
