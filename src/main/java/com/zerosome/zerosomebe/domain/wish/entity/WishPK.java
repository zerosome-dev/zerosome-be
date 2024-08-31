package com.zerosome.zerosomebe.domain.wish.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class WishPK implements java.io.Serializable {
    @Column(name = "PRD_IDX", nullable = false)
    private Long productId;

    @Column(name = "MEMBER_IDX", nullable = false)
    private Long memberId;
}
