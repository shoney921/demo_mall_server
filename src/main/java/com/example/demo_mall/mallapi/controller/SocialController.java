package com.example.demo_mall.mallapi.controller;

import com.example.demo_mall.mallapi.dto.*;
import com.example.demo_mall.mallapi.service.MemberService;
import com.example.demo_mall.security.util.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Member API", description = "회원 API")
@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class SocialController {

    private final MemberService memberService;

    @Operation(summary = "카카오 토큰으로 로그인", description = "카카오에서 받은 토큰으로 로그인합니다.")
    @GetMapping("/kakao")
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

    @Operation(summary = "회원 정보 변경", description = "회원 정보를 수정합니다.")
    @PutMapping("/modify")
    public ResultResDto modify(@RequestBody MemberModifyDto memberModifyDto) {
        memberService.modifyMember(memberModifyDto);
        return ResultResDto.builder().result("modified").build();
    }

    @Operation(summary = "닉네임 중복 여부 확인", description = "닉네임 중복 = true, 닉네임 없음 = false")
    @GetMapping("/duplicate")
    public ResultResDto checkDup(String nickname) {
        String result = memberService.isDuplicateNickname(nickname)? "true" : "false";
        return ResultResDto.builder().result(result).build();
    }

    @Operation(summary = "회원 등록", description = "회원 정보로 회원을 등록합니다.")
    @PostMapping("/signup")
    public ResultResDto signup(@RequestBody MemberSignupDto memberSignupDto) {
        Long id = memberService.create(memberSignupDto);
        return ResultResDto.builder().result( id.toString()).build();
    }

    @Operation(summary = "회원 아이디 조회", description = "이메일로 회원 아이디 조회합니다.")
    @GetMapping("/email/{email}")
    public ResultResDto getMemberIdByEmail(@PathVariable("email") String email) {
        Long id = memberService.getLongIdFromEmail(email);
        return ResultResDto.builder().result( id.toString()).build();
    }

}
