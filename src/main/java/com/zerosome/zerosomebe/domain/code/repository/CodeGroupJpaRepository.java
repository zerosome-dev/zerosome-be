package com.zerosome.zerosomebe.domain.code.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zerosome.zerosomebe.domain.code.entity.CodeGroup;

public interface CodeGroupJpaRepository extends JpaRepository<CodeGroup, Long> {
}
