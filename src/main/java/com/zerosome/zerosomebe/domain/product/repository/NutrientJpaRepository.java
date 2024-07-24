package com.zerosome.zerosomebe.domain.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zerosome.zerosomebe.domain.product.dto.NutrientByPdt;
import com.zerosome.zerosomebe.domain.product.entity.Nutrient;

public interface NutrientJpaRepository extends JpaRepository<Nutrient, Long> {
    @Query("select new com.zerosome.zerosomebe.domain.product.dto.NutrientByPdt("
           + "n.name, n.percentage, n.amount, n.percentageUnit, n.amountUnit) "
           + "FROM NUTRIENT n "
           + "WHERE n.productId in :productId "
           + "ORDER BY CASE "
           + "    WHEN n.name = '기준 용량' THEN 1"
           + "    WHEN n.name = '나트륨' THEN 2"
           + "    WHEN n.name = '탄수화물' THEN 3"
           + "    WHEN n.name = '당류' THEN 4"
           + "    WHEN n.name = '지방' THEN 5"
           + "    WHEN n.name = '트랜스지방' THEN 6"
           + "    WHEN n.name = '포화지방' THEN 7"
           + "    WHEN n.name = '콜레스테롤' THEN 8"
           + "    WHEN n.name = '단백질' THEN 9"
           + "    ELSE 10 END ASC, n.name ASC")
    List<NutrientByPdt> findNutrientByProduct(@Param("productId") Long productId);
}
