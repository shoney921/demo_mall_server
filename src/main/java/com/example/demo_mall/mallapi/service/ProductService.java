package com.example.demo_mall.mallapi.service;

import com.example.demo_mall.mallapi.dto.PageReqDto;
import com.example.demo_mall.mallapi.dto.PageResDto;
import com.example.demo_mall.mallapi.dto.ProductDto;
import jakarta.transaction.Transactional;

@Transactional
public interface ProductService {

    PageResDto<ProductDto> getList(PageReqDto pageReqDto);

    Long register(ProductDto productDto);

    ProductDto get(Long pno);

    void modify(ProductDto productDto);

    void remove(Long pno);
}
