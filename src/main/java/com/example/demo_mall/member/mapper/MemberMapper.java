package com.example.demo_mall.member.mapper;

import com.example.demo_mall.member.dto.Customer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    List<Customer> getAllMembers();

    Customer getMember(String id);
}
