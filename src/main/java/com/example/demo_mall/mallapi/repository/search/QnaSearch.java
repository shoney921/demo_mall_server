package com.example.demo_mall.mallapi.repository.search;

import com.example.demo_mall.mallapi.domain.Qna;
import com.example.demo_mall.mallapi.dto.PageReqDto;
import org.springframework.data.domain.Page;

public interface QnaSearch {

    Page<Qna> search1(PageReqDto pageReqDto);

}
