package com.zerosome.zerosomebe.domain.wish.entity;

import com.zerosome.zerosomebe.global.entity.BaseTimeEntity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "WISH")
public class Wish extends BaseTimeEntity {
    @Id
    @EmbeddedId
    private WishPK id;

    /*
       샘플 데이터

         INSERT INTO WISH (PRD_IDX, MEMBER_IDX, REG_DATE) VALUES (1, 1, NOW());

     */
}
