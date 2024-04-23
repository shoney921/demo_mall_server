package com.example.demo_mall.member;

import com.example.demo_mall.domain.Member;
import com.example.demo_mall.domain.MemberRole;
import com.example.demo_mall.member.dto.MemberDto;
import com.example.demo_mall.member.dto.MemberModifyDto;
import com.example.demo_mall.member.dto.MemberSignupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberDto getKakaoMember(String accessToken) {
        // todo 1. Access Token을 이용해서 사용자 정보를 가져오기
        Long kakaoLongId = getKakaoLongIdFromKakaoAccessToken(accessToken);

        // todo 2. 기존 DB에 회원 정보가 있는 경우
        Optional<Member> result = memberRepository.findByKakaoId(kakaoLongId);
        if (result.isPresent()) {
            return entityToDto(result.get());
        }

        // todo 3. 기존 DB에 회원 정보가 없는 경우
        Member socialMember = makeMember(kakaoLongId);
        memberRepository.save(socialMember);
        return entityToDto(socialMember);
    }

    @Override
    public void modifyMember(MemberModifyDto memberModifyDto) {
        Optional<Member> result = memberRepository.findById(memberModifyDto.getId());

        Member member = result.orElseThrow(NoSuchElementException::new);
        member.changeId(memberModifyDto.getId());
        member.changeNickname(memberModifyDto.getNickname());
        member.changeSocial(false);
        member.changPw(passwordEncoder.encode(memberModifyDto.getPassword()));
        member.changeMobile(memberModifyDto.getMobile());
        member.changeName(memberModifyDto.getName());

        memberRepository.save(member);
    }

    @Override
    public boolean isDuplicateNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    @Override
    public Long create(MemberSignupDto memberSignupDto) {
        Member member = Member.builder()
                .email(memberSignupDto.getEmail())
                .name(memberSignupDto.getName())
                .nickname(memberSignupDto.getNickname())
                .password(passwordEncoder.encode(memberSignupDto.getPassword()))
                .mobile(memberSignupDto.getMobile())
                .social(false)
                .build();
        member.addRole(MemberRole.USER);

        Member newMember = memberRepository.save(member);

        return newMember.getId();
    }

    private Member makeMember(Long kakaoId) {
        String tempPassword = makeTempPassword();

        log.info("tempPassword : " + tempPassword);

        Member member = Member.builder()
                .kakaoId(kakaoId)
                .email("")
                .password(passwordEncoder.encode(tempPassword))
                .nickname("")
                .social(true)
                .build();

        member.addRole(MemberRole.USER);

        return member;
    }

    /**
     * kakao에서 회원 정보 가져오기 (long id 만 사용하고 있음)
     * (추가 사용 정보가 필요하면 해당 메서드에서 사용하면 됨)
     * @param accessToken
     * @return
     */
    private Long getKakaoLongIdFromKakaoAccessToken(String accessToken) {
        String kakaoGetUserUrl = "https://kapi.kakao.com/v2/user/me";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(kakaoGetUserUrl).build();
        ResponseEntity<LinkedHashMap> response =
                restTemplate.exchange(uriBuilder.toUri(), HttpMethod.GET, entity, LinkedHashMap.class);
        log.info("response : " + response);
        LinkedHashMap<String, Long> bodyMap = response.getBody();

        return bodyMap.get("id");
    }

    private String makeTempPassword() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            buffer.append((char) ((int) (Math.random() * 55) + 65));
        }
        return buffer.toString();
    }

    @Override
    public Long getLongIdFromEmail(String email) {
        Optional<Member> byEmail = memberRepository.findByEmail(email);
        Member member = byEmail.orElseThrow(NoSuchElementException::new);
        return member.getId();
    }

    @Override
    public Member getMemberByEmail(String email) {
        Optional<Member> byEmail = memberRepository.findByEmail(email);
       return byEmail.orElseThrow(NoSuchElementException::new);
    }

    @Override
    public String findEmailByMemberInfo(String name, String mobile) {
        String email = memberRepository.getEmailByNameAndMobile(name, mobile)
                .orElseThrow(()->new NoSuchElementException("회원을 찾을 수 없습니다."));
        return email;
    }

    @Override
    public Boolean existMemberByMemberInfo(String name, String mobile, String email) {
        boolean b = memberRepository.existsMemberByEmailAndNameAndMobile(email, name, mobile);
        if(!b) throw new NoSuchElementException("회원을 찾을 수 없습니다.");
        return b;
    }
}
