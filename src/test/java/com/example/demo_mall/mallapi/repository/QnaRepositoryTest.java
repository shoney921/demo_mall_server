package com.example.demo_mall.mallapi.repository;

import com.example.demo_mall.mallapi.domain.Qna;
import com.example.demo_mall.mallapi.dto.QnaDto;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class QnaRepositoryTest {

    @Autowired
    private QnaRepository qnaRepository;

    @Autowired
    private ModelMapper modelMapper;

//    @Test // 테스트 데이터 생성을 위한 메서드
////    @Transactional
//    public void test01_insert() {
//        for (int i = 0; i <= 100; i++) {
//            Todo todo = Todo.builder()
//                    .title("Title-" + i)
//                    .dueDate(LocalDate.now())
//                    .writer("user00")
//                    .build();
//
//            todoRepository.save(todo);
//        }
//    }

    @Test
    @DisplayName("[db의존] 정상 조회 확인")
    public void testRead() {
        Optional<Qna> byId = qnaRepository.findById(33L);

        assertTrue(byId.isPresent(), "Todo 객체가 존재하지 않습니다.");

        byId.ifPresent(todo -> {
            assertEquals("Title-32", todo.getTitle(), "타이틀이 다릅");
        });
    }

    @Test
    @Transactional  // Todo : [팀싱크] 올바른 테스트가 맞는지, 영속성 컨텍스트에 관한 테스트를 하기 위해서는 Transactional 필요하다.
    public void testModify() {
        Optional<Qna> todo = qnaRepository.findById(34L);
        Qna testTodo = todo.orElseThrow();
        testTodo.updateTitle("Modified-34");
        testTodo.updateComplete(true);
        testTodo.updateDueDate(LocalDate.of(2024, 2, 9));
        // 여기서 특징으 영속성 컨텍스트를 사용하려면, 트랜잭션을 사용해야하고... 트랜잭션을 사용하게 되면 데이터 격리로 인해서 확인을 못함.

        assertEquals("Modified-34", qnaRepository.findById(34L).get().getTitle(), "제목이 올바르게 수정되지 않았습니다.");
    }

    @Test
    @Transactional
    public void testDelete() {
        Long id = 35L;
        Optional<Qna> todo = qnaRepository.findById(id);
        todo.orElseThrow();

        qnaRepository.deleteById(todo.get().getQno());

        assertFalse(qnaRepository.findById(id).isPresent(), "존재해서 실패했습니다. 존재하지 않으면 성공");
    }

    @Test
    public void testPaging() {
        // Todo : [팀싱크] 페이지 처리 방식
        Pageable pageable = PageRequest.of(0, 10, Sort.by("tno"));

        Page<Qna> result = qnaRepository.findAll(pageable);

        log.info("=======================");
        log.info(result.getTotalElements());
        log.info(result);
        result.forEach(log::info);
        result.getContent().forEach(log::info);
    }

    @Test
    public void testCopyBeanUtils() {
        Optional<Qna> byId = qnaRepository.findById(203L);
        Qna todo = byId.orElseThrow();
        QnaDto qnaDto = new QnaDto();

        BeanUtils.copyProperties(todo, qnaDto);

        log.info(todo);
        log.info(qnaDto);
    }

    @Test
    public void testCopyModelMapper() {
        Optional<Qna> byId = qnaRepository.findById(203L);
        Qna todo = byId.orElseThrow();
        QnaDto qnaDto = new QnaDto();

        modelMapper.map(todo, qnaDto);

        log.info(todo);
        log.info(qnaDto);
    }

    @Test
    public void testSearch1() {
//        Page<Todo> todos = todoRepository.search1();
    }

}