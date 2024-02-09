package com.example.demo_mall.mallapi.controller;

import com.example.demo_mall.mallapi.dto.PageReqDto;
import com.example.demo_mall.mallapi.dto.PageResDto;
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
    public Map<String, Long> register(@RequestBody TodoDto todoDto) {
        Long registeredTno = todoService.register(todoDto);
        return Map.of("tno", registeredTno); // todo : [팀싱크] map으로 반환 해달라고 할때 이렇게 쉽게 사용하는게 낫다
    }

    @PutMapping("/{tno}")
    public Map<String, String> modify(@PathVariable("tno") Long tno,
                                      @RequestBody TodoDto todoDto) {
        todoDto.setTno(tno);
        todoService.modify(todoDto);
        return Map.of("result", "success");
    }

    @DeleteMapping("/{tno}")
    public Map<String, String> remove(@PathVariable("tno") Long tno) {
        todoService.remove(tno);
        return Map.of("result", "success");
    }
}
