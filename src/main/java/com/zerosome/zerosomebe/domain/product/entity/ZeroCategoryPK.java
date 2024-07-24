package com.zerosome.zerosomebe.domain.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ZeroCategoryPK implements java.io.Serializable {
    @Column(name = "CATEGORY", nullable = false)
    private String zeroCategoryCode;

    @Column(name = "PRD_IDX", nullable = false)
    private Long productId;
}
