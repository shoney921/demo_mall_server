package com.example.demo_mall.member.repository;

import com.example.demo_mall.member.dto.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByMemberId(Long id);
}
