package com.example.demo_mall.mallapi.controller;

import com.example.demo_mall.mallapi.dto.MemberDto;
import com.example.demo_mall.mallapi.service.MemberService;
import com.example.demo_mall.security.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
public class SocialController {

    private final MemberService memberService;

    @GetMapping("/api/member/kakao")
    public Map<String, Object> getMemberFromKakao(String accessToken) {
        log.info("accessToken : " + accessToken);
        MemberDto kakaoMember = memberService.getKakaoMember(accessToken);

        Map<String, Object> claims = kakaoMember.getClaims();

        String jwtAccessToken = JWTUtil.generateToken(claims, 10);
        String jwtRefreshToken = JWTUtil.generateToken(claims,60*24);

        claims.put("accessToken", jwtAccessToken);
        claims.put("refreshToken", jwtRefreshToken);

        return claims;
    }
}
