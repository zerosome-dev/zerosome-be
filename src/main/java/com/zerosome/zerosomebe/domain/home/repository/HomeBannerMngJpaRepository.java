package com.zerosome.zerosomebe.domain.home.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zerosome.zerosomebe.domain.home.entity.HomeBannerMng;

public interface HomeBannerMngJpaRepository extends JpaRepository<HomeBannerMng, Long> {
    @Query("select b from HOME_BANNER_MNG b order by b.orderSeq asc")
    List<HomeBannerMng> findAllBanners();
}
