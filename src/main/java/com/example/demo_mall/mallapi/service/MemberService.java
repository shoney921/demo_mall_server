package com.example.demo_mall.mallapi.service;


import com.example.demo_mall.mallapi.dto.MemberDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MemberService {

    MemberDto getKakaoMember(String accessToken);
}
