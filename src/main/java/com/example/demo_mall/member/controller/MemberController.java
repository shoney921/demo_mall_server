package com.example.demo_mall.member.controller;

import com.example.demo_mall.member.dto.Customer;
import com.example.demo_mall.member.dto.ReqMemberDto;
import com.example.demo_mall.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Customer> getMember(@PathVariable("id") String id) {
        Optional<Customer> member = memberService.getMember(id);
        return member.map(m -> new ResponseEntity<>(m, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/jpa")
    public ResponseEntity<Customer> getMemberJpa(@PathVariable("id") String id) {
        Optional<Customer> memberJpa = memberService.getMemberJpa(id);
        return memberJpa.map(m -> new ResponseEntity<>(m, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

//    @PostMapping
//    public ResponseEntity<Customer> addMember(ReqMemberDto reqMemberDto) {
//        Optional<Customer> memberJpa = memberService.(reqMemberDto);
//        return memberJpa.map(m -> new ResponseEntity<>(m, HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
}
