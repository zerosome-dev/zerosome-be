package com.zerosome.zerosomebe.domain.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zerosome.zerosomebe.domain.product.dto.ProductByCtgResponse;
import com.zerosome.zerosomebe.domain.product.dto.ProductDetail;
import com.zerosome.zerosomebe.domain.product.dto.SimilarProductInterface;
import com.zerosome.zerosomebe.domain.product.entity.Product;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {
    @Query("select new com.zerosome.zerosomebe.domain.product.dto.ProductByCtgResponse("
           + "p.id, p.image, c.codeDesc, p.name, ROUND(AVG(rv.rating), 1), COUNT(rv.id)) "
           + "FROM PRODUCT p "
           + "LEFT JOIN COMM_CODE c ON c.code = p.brandCode "
           + "LEFT JOIN REVIEW rv ON rv.productId = p.id "
           + "LEFT JOIN ZERO_CATEGORY zc ON zc.id.productId = p.id AND zc.id.zeroCategoryCode in :zeroCtgList "
           + "WHERE p.categoryCode in :categoryCodeList "
           + "AND ((:brandList is null) or (p.brandCode in :brandList)) "
           + "GROUP BY p.id ")
    List<ProductByCtgResponse> findProductByCtgList(@Param("categoryCodeList") List<String> categoryCodeList,
                                                    @Param("brandList") List<String> brandList,
                                                    @Param("zeroCtgList") List<String> zeroCtgList,
                                                    Pageable pageable);

    @Query("select new com.zerosome.zerosomebe.domain.product.dto.ProductDetail("
           + "p.id, p.image, c.codeDesc, p.name, p.categoryCode, ROUND(AVG(rv.rating), 1), COUNT(rv.id)) "
           + "FROM PRODUCT p "
           + "LEFT JOIN COMM_CODE c ON c.code = p.brandCode "
           + "LEFT JOIN REVIEW rv ON rv.productId = p.id "
           + "WHERE p.id = :productId "
           + "GROUP BY p.id ")
    Optional<ProductDetail> findProductDetail(Long productId);

    @Query(value = "SELECT p.prd_idx as productId, "
                   + "p.image as image, "
                   + "c.code_desc as brandName, "
                   + "p.name as productName, "
                   + "ROUND(AVG(rv.rating)) as rating, "
                   + "COUNT(rv.rv_idx) as reviewCnt "
                   + "FROM product p "
                   + "LEFT JOIN review rv ON rv.prd_idx = p.prd_idx "
                   + "LEFT JOIN comm_code c ON c.code = p.brand "
                   + "WHERE p.category = :categoryCode AND p.prd_idx != :productId "
                   + "GROUP BY p.prd_idx ORDER BY RAND() LIMIT 10",
            nativeQuery = true)
    List<SimilarProductInterface> findSimilarProduct(@Param("productId") Long productId,
                                                     @Param("categoryCode") String categoryCode);
}
