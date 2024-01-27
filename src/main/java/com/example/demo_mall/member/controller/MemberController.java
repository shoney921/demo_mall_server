package com.example.demo_mall.member.controller;

import com.example.demo_mall.member.dto.Member;
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

    /**
     * 전체 맴버 조회
     * @return
     */
    @GetMapping
    public List<Member> getAllMembers() {
        return memberService.getAllMember();
    }

    /**
     * 맴버 조회
     * @param id
     * @return
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<Member> getMember(@PathVariable("memberId") String id) {
        Optional<Member> member = memberService.getMember(id);
        return member.map(m -> new ResponseEntity<>(m, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 맴버 생성
     * @param member
     * @return
     */
    @PostMapping
    public ResponseEntity<Integer> createMember(@RequestBody Member member) {
        Integer result = memberService.createMember(member);
        return result == 1 ? new ResponseEntity<>(result, HttpStatus.OK) :
                new ResponseEntity<>(result, HttpStatus.CONFLICT);
    }

}
