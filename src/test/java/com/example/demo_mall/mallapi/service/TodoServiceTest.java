package com.example.demo_mall.mallapi.service;

import com.example.demo_mall.mallapi.dto.TodoDto;
import com.example.demo_mall.mallapi.repository.TodoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
@Log4j2
class TodoServiceTest {

    @Autowired
    private TodoService todoService;
    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void testFind() {
        Long tno = 230L;

        TodoDto todoDto = todoService.find(tno);

        Assertions.assertEquals(tno, todoDto.getTno());
    }

    @Test
    @Transactional
    public void testRegister() {
        TodoDto todoDto = TodoDto.builder()
                .title("service Test transaction")
                .writer("test")
                .dueDate(LocalDate.of(2024, 02, 10))
                .build();

        Long tno = todoService.register(todoDto);
        todoDto.setTno(tno);

        Assertions.assertEquals(todoDto, todoService.find(tno));
    }

    @Test
    @Transactional
    public void testModify() {
        TodoDto sourceDto = TodoDto.builder()
                .tno(230L)
                .title("modify test")
                .writer("test01")
                .build();

        todoService.modify(sourceDto);

        TodoDto targetDto = todoService.find(sourceDto.getTno());
        Assertions.assertEquals(sourceDto, targetDto);
    }

    @Test
    @Transactional
    public void testDelete() {
        Long tno = 230L;

        todoService.remove(tno);

        boolean exists = todoRepository.findById(tno).isPresent();
        Assertions.assertFalse(exists);
    }


}