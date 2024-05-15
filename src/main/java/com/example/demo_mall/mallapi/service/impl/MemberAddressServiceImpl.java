package com.example.demo_mall.mallapi.service.impl;

import com.example.demo_mall.mallapi.domain.Member;
import com.example.demo_mall.mallapi.domain.MemberAddress;
import com.example.demo_mall.mallapi.dto.memberaddress.MemberAddressAddReqDto;
import com.example.demo_mall.mallapi.dto.memberaddress.MemberAddressModifyReqDto;
import com.example.demo_mall.mallapi.dto.memberaddress.MemberAddressResDto;
import com.example.demo_mall.mallapi.repository.MemberAddressRepository;
import com.example.demo_mall.mallapi.service.MemberAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberAddressServiceImpl implements MemberAddressService {

    private final MemberAddressRepository memberAddressRepository;

    @Override
    public List<MemberAddressResDto> findAllAddressByMemberId(Long memberId) {
        List<MemberAddress> byMemberId = memberAddressRepository.findByMemberId(memberId);
        return byMemberId.stream().map(this::entityToDto).toList();
    }

    private MemberAddressResDto entityToDto(MemberAddress memberAddress) {
        MemberAddressResDto memberAddressResDto = new MemberAddressResDto();
        BeanUtils.copyProperties(memberAddress, memberAddressResDto);
        return memberAddressResDto;
    }

    @Override
    public List<MemberAddressResDto> removeAddress(Long memberId, Long ano) {
        memberAddressRepository.deleteById(ano);
        return findAllAddressByMemberId(memberId);
    }

    @Override
    public List<MemberAddressResDto> creatAddress(MemberAddressAddReqDto memberAddressAddReqDto) {
        Member member = Member.builder().id(memberAddressAddReqDto.getMemberId()).build();
        MemberAddress memberAddress = makeMemberAddress(memberAddressAddReqDto, member);
        memberAddressRepository.save(memberAddress);
        return findAllAddressByMemberId(memberAddressAddReqDto.getMemberId());
    }

    private MemberAddress makeMemberAddress(MemberAddressAddReqDto memberAddressAddReqDto, Member member) {
        MemberAddress memberAddress = new MemberAddress();
        memberAddress.changeAddress(memberAddressAddReqDto.getAddress());
        memberAddress.changeAddressDetail(memberAddressAddReqDto.getAddressDetail());
        memberAddress.changeName(memberAddressAddReqDto.getName());
        memberAddress.changeZipCode(memberAddressAddReqDto.getZipCode());
        memberAddress.changeMember(member);
        return memberAddress;
    }

    @Override
    public List<MemberAddressResDto> updateAddress(MemberAddressModifyReqDto modifyReqDto) {
        Optional<MemberAddress> addressOptional = memberAddressRepository.findById(modifyReqDto.getAno());
        MemberAddress memberAddress = addressOptional.orElseThrow(NoSuchElementException::new);
        memberAddress.changeAddress(modifyReqDto.getAddress());
        memberAddress.changeAddressDetail(modifyReqDto.getAddressDetail());
        memberAddress.changeZipCode(modifyReqDto.getZipCode());
        memberAddress.changeName(modifyReqDto.getName());
        memberAddressRepository.save(memberAddress);
        return findAllAddressByMemberId(modifyReqDto.getMemberId());
    }
}
