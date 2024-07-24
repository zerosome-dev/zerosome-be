package com.zerosome.zerosomebe.domain.product.service.impl;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zerosome.zerosomebe.domain.product.dto.NutrientByPdt;
import com.zerosome.zerosomebe.domain.product.dto.OfflineStore;
import com.zerosome.zerosomebe.domain.product.dto.OnlineStore;
import com.zerosome.zerosomebe.domain.product.dto.ProductDetail;
import com.zerosome.zerosomebe.domain.product.dto.ProductDetailResponse;
import com.zerosome.zerosomebe.domain.product.dto.ReviewThumbnail;
import com.zerosome.zerosomebe.domain.product.dto.SimilarProduct;
import com.zerosome.zerosomebe.domain.product.dto.SimilarProductInterface;
import com.zerosome.zerosomebe.domain.product.repository.NutrientJpaRepository;
import com.zerosome.zerosomebe.domain.product.repository.ProductJpaRepository;
import com.zerosome.zerosomebe.domain.product.repository.SalesStoreJpaRepository;
import com.zerosome.zerosomebe.domain.product.service.ProductDetailService;
import com.zerosome.zerosomebe.domain.review.repository.ReviewJpaRepository;
import com.zerosome.zerosomebe.global.error.exception.ProductNotFoundException;
import com.zerosome.zerosomebe.global.s3.S3Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductDetailServiceImpl implements ProductDetailService {
    private final S3Service s3Service;
    private final NutrientJpaRepository nutrientRepository;
    private final SalesStoreJpaRepository salesStoreRepository;
    private final ReviewJpaRepository reviewRepository;
    private final ProductJpaRepository productRepository;

    @Override
    public ProductDetailResponse getProductDetail(Long productId) {
        ProductDetail productDetail = productRepository.findProductDetail(productId)
                                                       .orElseThrow(ProductNotFoundException::new);
        productDetail.setImage(s3Service.generateGetPresignedUrl(productDetail.getImage()));

        List<NutrientByPdt> nutrientList = nutrientRepository.findNutrientByProduct(productId);
        List<OfflineStore> offlineStoreList = salesStoreRepository.findOfflineStoreByProduct(productId);
        List<OnlineStore> onlineStoreList = salesStoreRepository.findOnlineStoreByProduct(productId);
        List<ReviewThumbnail> reviewThumbnailList
                = reviewRepository.findThumbnailByProduct(productId, PageRequest.of(0, 5));
        List<SimilarProductInterface> similarProductInterfaceList
                = productRepository.findSimilarProduct(productId, productDetail.getCategoryCode());

        List<SimilarProduct> similarProductList = buildSimilarProduct(similarProductInterfaceList);

        return new ProductDetailResponse(productDetail, nutrientList, offlineStoreList, onlineStoreList,
                                         reviewThumbnailList, similarProductList);
    }

    private List<SimilarProduct> buildSimilarProduct(
            List<SimilarProductInterface> similarProductInterfaceList) {
        return similarProductInterfaceList
                .stream()
                .map(similar -> {
                    String image = s3Service.generateGetPresignedUrl(similar.getimage());
                    return new SimilarProduct(similar, image);
                })
                .toList();
    }
}
