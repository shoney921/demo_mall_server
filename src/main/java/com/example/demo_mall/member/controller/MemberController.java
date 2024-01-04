package com.example.demo_mall.member.controller;

import com.example.demo_mall.member.dto.Member;
import org.springframework.web.bind.annotation.*;

@ResponseBody
@RequestMapping("/members")
@RestController
public class MemberController {

    @GetMapping
    public String getAllMembers() {
        return "result";
    }


    @GetMapping("/{id}")
    public Member getMember(@PathVariable("id") String id) {
        Member member = new Member();
        member.setCustomerId(Integer.parseInt(id));
        member.setFirstName("상헌");
        return member;
    }

}
