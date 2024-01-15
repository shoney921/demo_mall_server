package com.example.demo_mall.member.mapper;

import com.example.demo_mall.member.dto.Customer;
import com.example.demo_mall.member.dto.ReqMemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {
    List<Customer> getAllMembers();

    Optional<Customer> getMember(String id);

    Optional<Customer> insertMember(ReqMemberDto reqMemberDto);
}
