package com.example.demo_mall.memberaddress;


import com.example.demo_mall.memberaddress.dto.MemberAddressAddReqDto;
import com.example.demo_mall.memberaddress.dto.MemberAddressModifyReqDto;
import com.example.demo_mall.memberaddress.dto.MemberAddressResDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MemberAddressService {
    List<MemberAddressResDto> findAllAddressByMemberId(Long memberId);

    List<MemberAddressResDto> removeAddress(Long memberId, Long ano);

    List<MemberAddressResDto> creatAddress(MemberAddressAddReqDto memberAddressAddReqDto);

    List<MemberAddressResDto> updateAddress(MemberAddressModifyReqDto memberAddressModifyReqDto);

}
