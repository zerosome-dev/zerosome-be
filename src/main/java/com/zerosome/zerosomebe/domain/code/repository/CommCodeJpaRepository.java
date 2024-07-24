package com.zerosome.zerosomebe.domain.code.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zerosome.zerosomebe.domain.code.entity.CommCode;

public interface CommCodeJpaRepository extends JpaRepository<CommCode, Long> {
    Optional<CommCode> findFirstByCode(String code);

    List<CommCode> findAllBySuperCode(String superCode);

    Optional<CommCode> findFirstByCodeDescAndGroupCode(String codeDesc, String groupCode);
}
