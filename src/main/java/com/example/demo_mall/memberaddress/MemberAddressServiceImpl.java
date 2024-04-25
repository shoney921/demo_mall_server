package com.example.demo_mall.memberaddress;

import com.example.demo_mall.domain.Member;
import com.example.demo_mall.domain.MemberAddress;
import com.example.demo_mall.domain.MemberRole;
import com.example.demo_mall.member.MemberRepository;
import com.example.demo_mall.member.MemberService;
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
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberAddressServiceImpl implements MemberAddressService {

    private final MemberAddressRepository memberAddressRepository;

    @Override
    public List<MemberAddressDto> findAllAddressByMemberId(Long memberId) {
        List<MemberAddress> byMemberId = memberAddressRepository.findByMemberId(memberId);
        return byMemberId.stream().map((m) -> entityToDto(memberId, m)).toList();
    }

    private MemberAddressDto entityToDto(Long memberId, MemberAddress memberAddress) {
        return MemberAddressDto.builder()
                .memberId(memberId)
                .ano(memberAddress.getAno())
                .address(memberAddress.getAddress())
                .addressDetail(memberAddress.getAddressDetail())
                .name(memberAddress.getName())
                .zipCode(memberAddress.getZipCode())
                .build();
    }

    @Override
    public List<MemberAddressDto> removeAddress(Long ano) {
        //todo 회원 정보 삭제
        //todo 회원 리스트 조회
        return null;
    }

    @Override
    public List<MemberAddressDto> creatAddress(MemberAddressDto memberAddressDto) {
        //todo 회원 정보 생성
        //todo 회원 리스트 조회
        return null;
    }

    @Override
    public List<MemberAddressDto> updateAddress(MemberAddressDto memberAddressDto) {
        //todo 회원 정보 생성
        //todo 회원 리스트 조회
        return null;
    }
}
