package com.example.demo_mall.mallapi.controller;

import com.example.demo_mall.mallapi.controller.advise.ApiResponse;
import com.example.demo_mall.mallapi.dto.memberaddress.MemberAddressAddReqDto;
import com.example.demo_mall.mallapi.dto.memberaddress.MemberAddressModifyReqDto;
import com.example.demo_mall.mallapi.dto.memberaddress.MemberAddressResDto;
import com.example.demo_mall.mallapi.service.MemberAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "Member Address API", description = "회원 주소 API")
@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/address")
public class MemberAddressController {

    private final MemberAddressService memberAddressService;

    @Operation(summary = "회원의 모든 주소 조회", description = "회원의 모든 주소 조회")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public ApiResponse<List<MemberAddressResDto>> getMemberAddress(Principal principal) {
        String memberId = principal.getName();
        return ApiResponse.success(memberAddressService.findAllAddressByMemberId(Long.parseLong(memberId)));
    }

    @Operation(summary = "회원 주소 추가", description = "회원의 주소를 추가 하고, 모든 주소 조회")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ApiResponse<List<MemberAddressResDto>> addMemberAddress(Principal principal
            , @RequestBody MemberAddressAddReqDto memberAddressAddReqDto) {
        String memberId = principal.getName();
        memberAddressAddReqDto.setMemberId(Long.parseLong(memberId));
        return ApiResponse.success(memberAddressService.creatAddress(memberAddressAddReqDto));
    }

    @Operation(summary = "회원 주소 삭제", description = "회원의 주소를 삭제 하고, 모든 주소 조회")
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{ano}")
    public ApiResponse<List<MemberAddressResDto>> removeMemberAddress(Principal principal
            , @PathVariable("ano") Long ano) {
        Long memberId = Long.parseLong(principal.getName());
        return ApiResponse.success(memberAddressService.removeAddress(memberId, ano));
    }

    @Operation(summary = "회원 주소 변경", description = "회원의 주소를 변경 하고, 모든 주소 조회")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping()
    public ApiResponse<List<MemberAddressResDto>> updateMemberAddress(Principal principal
            , @RequestBody MemberAddressModifyReqDto modifyReqDto) {
        Long memberId = Long.parseLong(principal.getName());
        modifyReqDto.setMemberId(memberId);
        return ApiResponse.success(memberAddressService.updateAddress(modifyReqDto));
    }
}
