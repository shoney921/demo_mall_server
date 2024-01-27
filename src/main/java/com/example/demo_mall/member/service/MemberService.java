package com.example.demo_mall.member.service;

import com.example.demo_mall.member.dto.Customer;
import com.example.demo_mall.member.dto.Member;
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

    public List<Member> getAllMember() {
        return memberMapper.getAllMember();
    }

    public Optional<Member> getMember(String id) {
        return memberMapper.getMemberById(id);
    }

    public Integer createMember(Member member) {
        return memberMapper.insertMember(member);
    }
}
