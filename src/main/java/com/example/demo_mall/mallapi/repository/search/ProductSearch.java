package com.example.demo_mall.mallapi.repository.search;

import com.example.demo_mall.mallapi.dto.PageReqDto;
import com.example.demo_mall.mallapi.dto.PageResDto;
import com.example.demo_mall.mallapi.dto.ProductDto;

public interface ProductSearch {

    PageResDto<ProductDto> searchList(PageReqDto pageReqDto);
}
