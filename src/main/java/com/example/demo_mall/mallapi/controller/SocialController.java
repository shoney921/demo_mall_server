package com.example.demo_mall.mallapi.controller;

import com.example.demo_mall.mallapi.dto.*;
import com.example.demo_mall.mallapi.service.MemberService;
import com.example.demo_mall.security.util.JWTUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
public class SocialController {

    private final MemberService memberService;

    @GetMapping("/api/member/kakao")
    public TokenResDto getMemberFromKakao(String accessToken) {
        log.info("accessToken : " + accessToken);
        MemberDto kakaoMember = memberService.getKakaoMember(accessToken);

        Map<String, Object> claims = kakaoMember.getClaims();

        String jwtAccessToken = JWTUtil.generateToken(claims, 10);
        String jwtRefreshToken = JWTUtil.generateToken(claims, 60 * 24);

        claims.put("accessToken", jwtAccessToken);
        claims.put("refreshToken", jwtRefreshToken);

        TokenResDto tokenResDto = TokenResDto.builder()
                .accessToken(jwtAccessToken)
                .refreshToken(jwtRefreshToken)
                .build();

        BeanUtils.copyProperties(kakaoMember, tokenResDto);

        return tokenResDto;
    }

    @PutMapping("/api/member/modify")
    public ResultResDto modify(@RequestBody MemberModifyDto memberModifyDto) {
        memberService.modifyMember(memberModifyDto);
        return ResultResDto.builder().result("modified").build();
    }

    @GetMapping("/api/member/duplicate")
    public ResultResDto checkDup(String nickname) {
        String result = memberService.isDuplicateNickname(nickname)? "true" : "false";
        return ResultResDto.builder().result(result).build();
    }

    @PostMapping("/api/member/signup")
    public ResultResDto signup(@RequestBody MemberSignupDto memberSignupDto) {
        Long id = memberService.create(memberSignupDto);
        return ResultResDto.builder().result( id.toString()).build();
    }

    @PostMapping("/api/member/email")
    public ResultResDto checkEmail(@RequestBody Map<String, String> requestBody) {
        Long id = memberService.getLongIdFromEmail(requestBody.get("email"));
        return ResultResDto.builder().result( id.toString()).build();
    }

}
