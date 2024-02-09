package com.example.demo_mall.mallapi.service;

import com.example.demo_mall.mallapi.domain.Todo;
import com.example.demo_mall.mallapi.dto.PageReqDto;
import com.example.demo_mall.mallapi.dto.PageResDto;
import com.example.demo_mall.mallapi.dto.TodoDto;
import com.example.demo_mall.mallapi.repository.TodoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class TodoService {

    private final ModelMapper modelMapper;
    private final TodoRepository todoRepository;

    public TodoDto find(Long tno) {
        Todo todo = todoRepository.findById(tno)
                .orElseThrow(() -> new NoSuchElementException("tno로 찾을 수 없습니다."));
        return modelMapper.map(todo, TodoDto.class);
    }

    public Long register(TodoDto todoDto) {
        Todo todo = modelMapper.map(todoDto, Todo.class);
        return todoRepository.save(todo).getTno();
    }

    public void modify(TodoDto todoDto) {
        Todo todo = todoRepository.findById(todoDto.getTno())
                .orElseThrow(() -> new NoSuchElementException("수정할 tno가 존재하지 않습니다."));
        modelMapper.map(todoDto, todo);
    }

    public void remove(Long tno) {
        todoRepository.deleteById(tno);
    }

    public PageResDto<TodoDto> getList(PageReqDto pageReqDto) {

        Page<Todo> result = todoRepository.search1(pageReqDto);

        List<TodoDto> list = result.getContent().stream().map(
                todo -> modelMapper.map(todo, TodoDto.class)
        ).toList();

        return PageResDto.<TodoDto>withAll()
                .dtoList(list)
                .pageReqDto(pageReqDto)
                .total(result.getTotalElements())
                .build();

    }

}
