package com.example.demo_mall.mallapi.controller;

import com.example.demo_mall.mallapi.dto.PageReqDto;
import com.example.demo_mall.mallapi.dto.PageResDto;
import com.example.demo_mall.mallapi.dto.ResultResDto;
import com.example.demo_mall.mallapi.dto.QnaDto;
import com.example.demo_mall.mallapi.service.QnaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Q&A API", description = "Q&A API")
@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/qna")
public class QnaController {

    private final QnaService qnaService;

    @Operation(summary = "Q&A 상세 조회", description = "Q&A 상세 조회")
    @GetMapping("/{qno}")
    public QnaDto get(@PathVariable("qno") Long qno) {
        return qnaService.find(qno);
    }

    @Operation(summary = "Q&A 리스트 조회", description = "Q&A 리스트 조회")
    @GetMapping("/list")
    public PageResDto<QnaDto> getList(PageReqDto pageReqDto) {
        return qnaService.getList(pageReqDto);
    }

    @Operation(summary = "Q&A 등록", description = "Q&A 등록")
    @PostMapping("/")
    public ResultResDto register(@RequestBody QnaDto qnaDto) {
        Long registeredqno = qnaService.register(qnaDto);
        return ResultResDto.builder()
                .result(registeredqno.toString())
                .build();
    }

    @Operation(summary = "Q&A 정보 변경", description = "Q&A 정보 변경")
    @PutMapping("/{qno}")
    public ResultResDto modify(@PathVariable("qno") Long qno,
                               @RequestBody QnaDto qnaDto) {
        qnaDto.setQno(qno);
        qnaService.modify(qnaDto);
        return ResultResDto.builder()
                .result("success")
                .build();
    }

    @Operation(summary = "Q&A 정보 삭제", description = "Q&A 정보 삭제")
    @DeleteMapping("/{qno}")
    public ResultResDto remove(@PathVariable("qno") Long qno) {
        qnaService.remove(qno);
        return ResultResDto.builder()
                .result("success")
                .build();
    }
}