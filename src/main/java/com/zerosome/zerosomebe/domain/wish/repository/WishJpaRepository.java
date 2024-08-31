package com.zerosome.zerosomebe.domain.wish.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zerosome.zerosomebe.domain.wish.entity.Wish;
import com.zerosome.zerosomebe.domain.wish.entity.WishPK;

public interface WishJpaRepository extends JpaRepository<Wish, WishPK> {
}
