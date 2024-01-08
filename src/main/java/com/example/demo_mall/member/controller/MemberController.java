package com.example.demo_mall.member.controller;

import com.example.demo_mall.member.dto.Customer;
import com.example.demo_mall.member.dto.Member;
import com.example.demo_mall.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ResponseBody
@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public List<Customer> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/jpa")
    public List<Customer> getAllMembersJpa() {
        return memberService.getAllMembersJpa();
    }

    @GetMapping("/{id}")
    public Customer getMember(@PathVariable("id") String id) {
        return memberService.getMember(id);
    }

}
