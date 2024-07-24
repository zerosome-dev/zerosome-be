package com.zerosome.zerosomebe.domain.home.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zerosome.zerosomebe.domain.home.dto.HomeCafeResponse;
import com.zerosome.zerosomebe.domain.home.dto.RolloutProduct;
import com.zerosome.zerosomebe.domain.product.entity.Product;

public interface HomeProductJpaRepository extends JpaRepository<Product, Long> {
    @Query("select new com.zerosome.zerosomebe.domain.home.dto.RolloutProduct("
           + "p.id, p.image, cd2.codeDesc, cd1.codeDesc, p.name) "
           + "from PRODUCT p "
           + "left join COMM_CODE cd1 on cd1.code = p.categoryCode and p.rolloutYn = true "
           + "left join COMM_CODE cd2 on cd2.code = cd1.superCode "
           + "left join SALES_STORE st on st.productId = p.id and st.storeCode = 'ST1' "
           + "where p.rolloutYn = true "
           + "order by st.id desc, p.regDate desc")
    List<RolloutProduct> findRolloutProductList();

    @Query("select new com.zerosome.zerosomebe.domain.home.dto.HomeCafeResponse( "
           + "p.id, p.image, p.categoryCode, cd1.superCode, p.name, cd2.codeDesc, "
           + "ROUND(AVG(rv.rating), 1), COUNT(rv.id) ) "
           + "FROM PRODUCT p "
           + "left join COMM_CODE cd1 on cd1.code = p.categoryCode "
           + "left join COMM_CODE cd2 on cd2.code = p.brandCode "
           + "left join REVIEW rv on rv.productId = p.id "
           + "WHERE cd1.superCode = 'CTG002' "
           + "GROUP BY p.id "
           + "ORDER BY p.regDate DESC, ROUND(AVG(rv.rating), 1) DESC")
    List<HomeCafeResponse> findCafeProductList();
}
