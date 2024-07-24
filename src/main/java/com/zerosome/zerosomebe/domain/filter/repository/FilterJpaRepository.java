package com.zerosome.zerosomebe.domain.filter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zerosome.zerosomebe.domain.code.entity.CommCode;
import com.zerosome.zerosomebe.domain.filter.dto.BrandFilterResponse;
import com.zerosome.zerosomebe.domain.filter.dto.D2CategoryFilterResponse;
import com.zerosome.zerosomebe.domain.filter.dto.ZeroCategoryFilterResponse;

public interface FilterJpaRepository extends JpaRepository<CommCode, Long> {
    @Query("select new com.zerosome.zerosomebe.domain.filter.dto.D2CategoryFilterResponse("
           + "c.code, c.codeDesc, c.subNoOptionYn) "
           + "from COMM_CODE c "
           + "where c.groupCode = 'CTG' and c.superCode = :d1CategoryCode "
           + "order by c.code asc")
    List<D2CategoryFilterResponse> findSubCategoryList(@Param("d1CategoryCode") String d1CategoryCode);

    @Query("select new com.zerosome.zerosomebe.domain.filter.dto.BrandFilterResponse("
           + "c.code, c.codeDesc) "
           + "from COMM_CODE c "
           + "where c.groupCode = 'BRAND' "
           + "order by c.codeDesc asc")
    List<BrandFilterResponse> findBrandList();

    @Query("select new com.zerosome.zerosomebe.domain.filter.dto.ZeroCategoryFilterResponse("
           + "c.code, c.codeDesc) "
           + "from COMM_CODE c "
           + "where c.groupCode = 'ZEROTAG' "
           + "order by c.code asc")
    List<ZeroCategoryFilterResponse> findZeroCategoryList();
}
