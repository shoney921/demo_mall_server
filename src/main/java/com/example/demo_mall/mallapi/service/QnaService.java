package com.example.demo_mall.mallapi.service;

import com.example.demo_mall.mallapi.domain.Qna;
import com.example.demo_mall.mallapi.dto.PageReqDto;
import com.example.demo_mall.mallapi.dto.PageResDto;
import com.example.demo_mall.mallapi.dto.QnaDto;
import com.example.demo_mall.mallapi.repository.QnaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class QnaService {

    private final ModelMapper modelMapper;
    private final QnaRepository qnaRepository;

    public QnaDto find(Long qno) {
        Qna todo = qnaRepository.findById(qno)
                .orElseThrow(() -> new NoSuchElementException("qno로 찾을 수 없습니다."));
        return modelMapper.map(todo, QnaDto.class);
    }

    public Long register(QnaDto qnaDto) {
        Qna todo = modelMapper.map(qnaDto, Qna.class);
        return qnaRepository.save(todo).getQno();
    }

    public void modify(QnaDto qnaDto) {
        Qna todo = qnaRepository.findById(qnaDto.getQno())
                .orElseThrow(() -> new NoSuchElementException("수정할 qno가 존재하지 않습니다."));
        modelMapper.map(qnaDto, todo);
    }

    public void remove(Long qno) {
        qnaRepository.deleteById(qno);
    }

    public PageResDto<QnaDto> getList(PageReqDto pageReqDto) {

        Page<Qna> result = qnaRepository.search1(pageReqDto);

        List<QnaDto> list = result.getContent().stream().map(
                todo -> modelMapper.map(todo, QnaDto.class)
        ).toList();

        return PageResDto.<QnaDto>withAll()
                .dtoList(list)
                .pageReqDto(pageReqDto)
                .total(result.getTotalElements())
                .build();

    }

}
