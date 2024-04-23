package com.example.demo_mall.member;

import com.example.demo_mall.mallapi.controller.advise.ApiResponse;
import com.example.demo_mall.domain.Member;
import com.example.demo_mall.member.dto.*;
import com.example.demo_mall.security.CustomUserDetailService;
import com.example.demo_mall.security.util.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member API", description = "회원 API")
@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    private final CustomUserDetailService customUserDetailService;

    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "로그인", description = "로그인")
    @PostMapping("/login")
    public ApiResponse<LoginResDto> login(@RequestBody LoginReqDto loginReqDto) {
        Member memberByEmail = memberService.getMemberByEmail(loginReqDto.getEmail());
        UserDetails userDetails = customUserDetailService.loadUserByUsername(memberByEmail.getId().toString());
        if (userDetails == null)
            return ApiResponse.error("404", "User no found");
        if (!passwordEncoder.matches(loginReqDto.getPassword(), userDetails.getPassword()))
            return ApiResponse.error("401", "Authentication failed");
        MemberDto memberDto = memberService.entityToDto(memberByEmail);
        LoginResDto loginResDto = JWTUtil.convertMemberDtoToLoginResDto(memberDto);
        return ApiResponse.success(loginResDto);
    }

    @Operation(summary = "카카오 토큰으로 로그인", description = "카카오에서 받은 토큰으로 로그인합니다.")
    @GetMapping("/kakao")
    public ApiResponse<LoginResDto> getMemberFromKakao(String accessToken) {
        MemberDto kakaoMember = memberService.getKakaoMember(accessToken);
        LoginResDto loginResDto = JWTUtil.convertMemberDtoToLoginResDto(kakaoMember);
        return ApiResponse.success(loginResDto);
    }

    @Operation(summary = "회원 정보 변경", description = "회원 정보를 수정합니다.")
    @PutMapping("/modify")
    public ApiResponse<?> modify(@RequestBody MemberModifyDto memberModifyDto) {
        memberService.modifyMember(memberModifyDto);
        return ApiResponse.success(null);
    }

    @Operation(summary = "닉네임 중복 여부 확인", description = "닉네임 중복 = true, 닉네임 없음 = false")
    @GetMapping("/duplicate")
    public ApiResponse<Boolean> checkDup(String nickname) {
        boolean duplicateNickname = memberService.isDuplicateNickname(nickname);
        return ApiResponse.success(duplicateNickname);
    }

    @Operation(summary = "회원 등록", description = "회원 정보로 회원을 등록합니다.")
    @PostMapping("/signup")
    public ApiResponse<Long> signup(@RequestBody MemberSignupDto memberSignupDto) {
        Long id = memberService.create(memberSignupDto);
        return ApiResponse.success(id);
    }

    @Operation(summary = "회원 아이디 조회", description = "이메일로 회원 아이디 조회합니다.")
    @GetMapping("/email/{email}")
    public ApiResponse<Long> getMemberIdByEmail(@PathVariable("email") String email) {
        Long id = memberService.getLongIdFromEmail(email);
        return ApiResponse.success(id);
    }

    @Operation(summary = "아이디 찾기", description = "회원명과 전화번호로 회원 아이디를 찾습니다.")
    @GetMapping("/email")
    public ApiResponse<String> findIdByMemberInfo(@RequestParam("name") String name, @RequestParam("mobile") String mobile) {
        String email = memberService.findEmailByMemberInfo(name, mobile);
        return ApiResponse.success(email);
    }

    @Operation(summary = "비밀번호 찾기", description = "이메일로 등록된 회원의 비밀번호를 재설정할 수 있는 링크를 전송합니다.")
    @PostMapping("/reset-password")
    public ApiResponse<Boolean> resetPassword(@RequestParam("name") String name,@RequestParam("mobile") String mobile,@RequestParam("email") String email) {
        Boolean b = memberService.existMemberByMemberInfo(name, mobile, email);
        return ApiResponse.success(b);
    }
}
