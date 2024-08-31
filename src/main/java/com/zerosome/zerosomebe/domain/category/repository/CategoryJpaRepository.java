package com.zerosome.zerosomebe.domain.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zerosome.zerosomebe.domain.category.dto.AllFoodCategory;
import com.zerosome.zerosomebe.domain.code.entity.CommCode;

public interface CategoryJpaRepository extends JpaRepository<CommCode, Long> {
    @Query("select new com.zerosome.zerosomebe.domain.category.dto.AllFoodCategory("
           + "d1.code, d1.codeDesc, d2.code, d2.codeDesc, concat('prod/category/', d2.code,'.png'), d2.subNoOptionYn) "
           + "from COMM_CODE d1 "
           + "left join COMM_CODE d2 on d1.code = d2.superCode "
           + "where d1.groupCode = 'CTG' and d1.superCode is null "
           + "order by d1.code asc")
    List<AllFoodCategory> findAllFoodCategoryList();

}
