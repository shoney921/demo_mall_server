package com.example.demo_mall.member.mapper;

import com.example.demo_mall.member.dto.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    List<Member> getAllMembers();

    Member getMember(String id);
}
