package com.zerosome.zerosomebe.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zerosome.zerosomebe.domain.member.entity.Member;
import com.zerosome.zerosomebe.domain.member.entity.SocialType;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByProviderAndSocialId(SocialType provider, String socialId);

    boolean existsByNickname(String nickname);
}
