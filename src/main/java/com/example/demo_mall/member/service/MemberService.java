package com.example.demo_mall.member.service;

import com.example.demo_mall.member.dto.Member;
import com.example.demo_mall.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberMapper memberMapper;

    public List<Member> getAllMembers() {
        return memberMapper.getAllMembers();
    }

    public Member getMember(String id) {
        return memberMapper.getMember(id);
    }
}
