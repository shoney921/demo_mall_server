package com.example.demo_mall.mallapi.repository.search;

import com.example.demo_mall.mallapi.domain.Todo;
import com.example.demo_mall.mallapi.dto.PageReqDto;
import org.springframework.data.domain.Page;

public interface TodoSearch {

    Page<Todo> search1(PageReqDto pageReqDto);

}
