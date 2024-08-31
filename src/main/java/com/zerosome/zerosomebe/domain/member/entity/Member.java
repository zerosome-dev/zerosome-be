package com.zerosome.zerosomebe.domain.member.entity;

import com.zerosome.zerosomebe.global.entity.ModTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "MEMBER")
public class Member extends ModTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_IDX")
    private Long id;

    @Column(name = "EMAIL")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "PROVIDER", columnDefinition = "varchar(100)")
    private SocialType provider;

    @Column(name = "SOCIAL_ID")
    private String socialId;

    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;

    @Column(name = "MANAGER_YN")
    private boolean managerYn;

    @Column(name = "NICKNAME")
    private String nickname;

    @Column(name = "AGREEM_MARKETING")
    private boolean agreeMarketing;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "AGE")
    private int age;

    @Builder
    public Member(SocialType socialType, String socialId, String email, String nickname, boolean agreeMarketing,
                  String gender, int age) {
        this.provider = socialType;
        this.socialId = socialId;
        this.email = email;
        this.nickname = nickname;
        this.agreeMarketing = agreeMarketing;
        this.gender = gender;
        this.age = age;
    }

    public void setMemberInfo(String email, String nickname, boolean agreeMarketing, String gender, int age) {
        this.email = email;
        this.nickname = nickname;
        this.agreeMarketing = agreeMarketing;
        this.gender = gender;
        this.age = age;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void resetRefreshToken() {
        this.refreshToken = null;
    }

    public void setManager() {
        this.managerYn = true;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }


    /* 샘플 데이터 쿼리

    INSERT INTO MEMBER
    (MEMBER_IDX, EMAIL, PROVIDER, SOCIAL_ID, REFRESH_TOKEN, MANAGER_YN, NICKNAME,
    AGREEM_MARKETING, GENDER, AGE, REG_DATE, MOD_DATE)
    VALUES
    (1, 'sample@gmail.com', 'KAKAO', '123456', 'sampletoken', false, 'mynickname',
    true, 'F', 20, NOW(), NOW());

     */
}
