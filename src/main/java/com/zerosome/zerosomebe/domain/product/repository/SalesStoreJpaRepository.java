package com.zerosome.zerosomebe.domain.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zerosome.zerosomebe.domain.home.dto.RolloutProductStore;
import com.zerosome.zerosomebe.domain.product.dto.OfflineStore;
import com.zerosome.zerosomebe.domain.product.dto.OnlineStore;
import com.zerosome.zerosomebe.domain.product.entity.SalesStore;

public interface SalesStoreJpaRepository extends JpaRepository<SalesStore, Long> {
    @Query("select new com.zerosome.zerosomebe.domain.home.dto.RolloutProductStore("
           + "st.productId, cd.codeDesc) "
           + "from SALES_STORE st "
           + "left join COMM_CODE cd on cd.code = st.storeCode "
           + "where st.productId in :productIdList "
           + "order by st.productId asc, "
           + "case cd.codeDesc when '출시예정' then 1 else 2 end, "
           + "cd.codeDesc asc")
    List<RolloutProductStore> findRolloutProductStoreList(@Param("productIdList") List<Long> productIdList);

    @Query("select new com.zerosome.zerosomebe.domain.product.dto.OfflineStore("
           + "case when cd.codeDesc is not NULL then st.storeCode else NULL end, "
           + "ifnull(cd.codeDesc, st.storeCode) ) "
           + "from SALES_STORE st "
           + "left join COMM_CODE cd on cd.code = st.storeCode "
           + "where st.productId = :productId and st.offlineYn = true "
           + "order by st.id asc")
    List<OfflineStore> findOfflineStoreByProduct(@Param("productId") Long productId);

    @Query("select new com.zerosome.zerosomebe.domain.product.dto.OnlineStore("
           + "case when cd.codeDesc is not NULL then st.storeCode else NULL end, "
           + "ifnull(cd.codeDesc, st.storeCode), "
           + "st.url) "
           + "from SALES_STORE st "
           + "left join COMM_CODE cd on cd.code = st.storeCode "
           + "where st.productId = :productId and st.offlineYn = false "
           + "order by st.id asc")
    List<OnlineStore> findOnlineStoreByProduct(@Param("productId") Long productId);
}
