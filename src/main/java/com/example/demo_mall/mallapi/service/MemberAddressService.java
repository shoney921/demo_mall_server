package com.example.demo_mall.mallapi.service;


import com.example.demo_mall.mallapi.dto.memberaddress.MemberAddressAddReqDto;
import com.example.demo_mall.mallapi.dto.memberaddress.MemberAddressModifyReqDto;
import com.example.demo_mall.mallapi.dto.memberaddress.MemberAddressResDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MemberAddressService {
    List<MemberAddressResDto> findAllAddressByMemberId(Long memberId);

    List<MemberAddressResDto> removeAddress(Long memberId, Long ano);

    List<MemberAddressResDto> creatAddress(MemberAddressAddReqDto memberAddressAddReqDto);

    List<MemberAddressResDto> updateAddress(MemberAddressModifyReqDto memberAddressModifyReqDto);

}
