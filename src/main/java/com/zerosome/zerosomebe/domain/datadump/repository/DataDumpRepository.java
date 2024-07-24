package com.zerosome.zerosomebe.domain.datadump.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zerosome.zerosomebe.domain.product.entity.Product;

public interface DataDumpRepository extends JpaRepository<Product, Long> {

}
