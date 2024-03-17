package com.example.demo_mall.mallapi.service;


import com.example.demo_mall.mallapi.domain.Member;
import com.example.demo_mall.mallapi.dto.MemberDto;
import com.example.demo_mall.mallapi.dto.MemberModifyDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Transactional
public interface MemberService {

    MemberDto getKakaoMember(String accessToken);

    void modifyMember(MemberModifyDto memberModifyDto);

    boolean isDuplicateNickname(String nickname);

    default MemberDto entityToDto(Member member) {
        return new MemberDto(
                member.getEmail(),
                member.getPw(),
                member.getNickname(),
                member.isSocial(),
                member.getMemberRoleList().stream().map(
                        Enum::name
                ).collect(Collectors.toList())
        );
    }
}
