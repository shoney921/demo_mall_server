package com.example.demo_mall.memberaddress;


import com.example.demo_mall.domain.Member;
import com.example.demo_mall.domain.MemberAddress;
import com.example.demo_mall.member.dto.MemberDto;
import com.example.demo_mall.member.dto.MemberModifyDto;
import com.example.demo_mall.member.dto.MemberSignupDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
public interface MemberAddressService {
    List<MemberAddressDto> findAllAddressByMemberId(Long memberId);

    List<MemberAddressDto> removeAddress(Long ano);

    List<MemberAddressDto> creatAddress(MemberAddressDto memberAddressDto);

    List<MemberAddressDto> updateAddress(MemberAddressDto memberAddressDto);


}
