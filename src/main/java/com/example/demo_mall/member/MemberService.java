package com.example.demo_mall.member;


import com.example.demo_mall.domain.Member;
import com.example.demo_mall.member.dto.MemberDto;
import com.example.demo_mall.member.dto.MemberModifyDto;
import com.example.demo_mall.member.dto.MemberSignupDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
public interface MemberService {

    MemberDto getKakaoMember(String accessToken);

    void modifyMember(MemberModifyDto memberModifyDto);

    boolean isDuplicateNickname(String nickname);

    default MemberDto entityToDto(Member member) {
        return new MemberDto(
                member.getId(),
                member.getKakaoId(),
                member.getEmail(),
                member.getMobile(),
                member.getPassword(),
                member.getName(),
                member.getNickname(),
                member.isSocial(),
                member.getMemberRoleList().stream().map(
                        Enum::name
                ).collect(Collectors.toList())
        );
    }

    Long create(MemberSignupDto memberSignupDto);

    Long getLongIdFromEmail(String email);

    Member getMemberByEmail(String email);


}
