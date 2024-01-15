package com.example.demo_mall.member.service;

import com.example.demo_mall.member.dto.Customer;
import com.example.demo_mall.member.dto.ReqMemberDto;
import com.example.demo_mall.member.mapper.MemberMapper;
import com.example.demo_mall.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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

    public Optional<Customer> getMember(String id) {
        return memberMapper.getMember(id);
    }
    public Optional<Customer> getMemberJpa(String id) {
        return memberRepository.findCustomerByCustomerNumber(Long.parseLong(id));
    }
}
