package com.example.demo_mall.memberaddress;

import com.example.demo_mall.mallapi.controller.advise.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ApiResponse<List<MemberAddressDto>> getMemberAddress(Principal principal) {
        String memberId = principal.getName();
        System.out.println("memberId = " + memberId);
        return ApiResponse.success(memberAddressService.findAllAddressByMemberId(Long.parseLong(memberId)));
    }
}
