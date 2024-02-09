package com.example.demo_mall.sample.member.repository;

import com.example.demo_mall.sample.member.dto.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {
    List<Member> getAllMember();

    Optional<Member> getMemberById(String id);

    Integer insertMember(Member member);

}
