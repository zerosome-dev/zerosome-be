package com.zerosome.zerosomebe.global.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;

public abstract class ModTimeEntity extends BaseTimeEntity {

    @LastModifiedDate
    @Column(name = "MOD_DATE", nullable = true)
    private LocalDateTime modDate;
}
