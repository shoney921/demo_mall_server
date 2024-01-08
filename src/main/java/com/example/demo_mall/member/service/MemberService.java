package com.example.demo_mall.member.service;

import com.example.demo_mall.member.dto.Customer;
import com.example.demo_mall.member.mapper.MemberMapper;
import com.example.demo_mall.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberMapper memberMapper;
    private final MemberRepository memberRepository;

    public List<Customer> getAllMembers() {
        return memberMapper.getAllMembers();
    }
    public List<Customer> getAllMembersJpa() {
        return memberRepository.findAll();
    }

    public Customer getMember(String id) {
        return memberMapper.getMember(id);
    }
    public Customer getMemberJpa(String id) {
        return memberRepository.findCustomerByCustomerNumber(Long.parseLong(id));
    }
}
