package com.example.demo_mall.mallapi.service;

import com.example.demo_mall.mallapi.dto.PageReqDto;
import com.example.demo_mall.mallapi.dto.QnaDto;
import com.example.demo_mall.mallapi.repository.QnaRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
@Log4j2
class QnaServiceTest {

    @Autowired
    private QnaService qnaService;
    @Autowired
    private QnaRepository qnaRepository;

    @Test
    public void testFind() {
        Long tno = 230L;

        QnaDto qnaDto = qnaService.find(tno);

        Assertions.assertEquals(tno, qnaDto.getQno());
    }

    @Test
    @Transactional
    public void testRegister() {
        QnaDto qnaDto = QnaDto.builder()
                .title("service Test transaction")
                .writer("test")
                .dueDate(LocalDate.of(2024, 02, 10))
                .build();

        Long tno = qnaService.register(qnaDto);
        qnaDto.setQno(tno);

        Assertions.assertEquals(qnaDto, qnaService.find(tno));
    }

    @Test
    @Transactional
    public void testModify() {
        QnaDto sourceDto = QnaDto.builder()
                .qno(230L)
                .title("modify test")
                .writer("test01")
                .build();

        qnaService.modify(sourceDto);

        QnaDto targetDto = qnaService.find(sourceDto.getQno());
        Assertions.assertEquals(sourceDto, targetDto);
    }

    @Test
    @Transactional
    public void testDelete() {
        Long tno = 230L;

        qnaService.remove(tno);

        boolean exists = qnaRepository.findById(tno).isPresent();
        Assertions.assertFalse(exists);
    }

    @Test
    public void testGetList() {
        PageReqDto pageReqDto = PageReqDto.builder()
                .page(10)
                .size(10)
                .build();

        log.info(qnaService.getList(pageReqDto));
    }

}