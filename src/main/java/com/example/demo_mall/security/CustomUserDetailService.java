package com.example.demo_mall.security;

import com.example.demo_mall.mallapi.domain.Member;
import com.example.demo_mall.mallapi.dto.MemberDto;
import com.example.demo_mall.mallapi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("=====================loadUserByUsername========================");
        log.info("user name" + username);
        Member member = memberRepository.getWithRoles(username);

        if (member == null) {
            throw new UsernameNotFoundException("Not Found");
        }
        MemberDto memberDto = new MemberDto(
                member.getEmail(), member.getPw(), member.getNickname(), member.isSocial(),
                member.getMemberRoleList().stream().map(Enum::name).toList());

        log.info(memberDto);

        return memberDto;

    }
}
