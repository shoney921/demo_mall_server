package com.example.demo_mall.mallapi.controller;

import com.example.demo_mall.mallapi.dto.PageReqDto;
import com.example.demo_mall.mallapi.dto.PageResDto;
import com.example.demo_mall.mallapi.dto.ResultResDto;
import com.example.demo_mall.mallapi.dto.TodoDto;
import com.example.demo_mall.mallapi.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/{tno}")
    public TodoDto get(@PathVariable("tno") Long tno) {
        return todoService.find(tno);
    }

    @GetMapping("/list")
    public PageResDto<TodoDto> getList(PageReqDto pageReqDto) {
        return todoService.getList(pageReqDto);
    }

    @PostMapping("/")
    public ResultResDto register(@RequestBody TodoDto todoDto) {
        Long registeredTno = todoService.register(todoDto);
        return ResultResDto.builder()
                .result(registeredTno.toString())
                .build();
    }

    @PutMapping("/{tno}")
    public ResultResDto modify(@PathVariable("tno") Long tno,
                               @RequestBody TodoDto todoDto) {
        todoDto.setTno(tno);
        todoService.modify(todoDto);
        return ResultResDto.builder()
                .result("success")
                .build();
    }

    @DeleteMapping("/{tno}")
    public ResultResDto remove(@PathVariable("tno") Long tno) {
        todoService.remove(tno);
        return ResultResDto.builder()
                .result("success")
                .build();
    }
}
