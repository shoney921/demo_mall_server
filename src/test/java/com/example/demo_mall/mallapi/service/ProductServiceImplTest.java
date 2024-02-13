package com.example.demo_mall.mallapi.service;

import com.example.demo_mall.mallapi.dto.PageReqDto;
import com.example.demo_mall.mallapi.dto.PageResDto;
import com.example.demo_mall.mallapi.dto.ProductDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testList() {

        PageReqDto pageReqDto = PageReqDto.builder().build();

        PageResDto<ProductDto> resDto = productService.getList(pageReqDto);

        log.info(resDto.getDtoList());
    }

    @Test
    public void testRegister() {
        ProductDto productDto = ProductDto.builder()
                .pdesc("신규 추가 상품")
                .pname("name test")
                .price(1000)
                .build();

        productDto.setUploadedFileNames(
                List.of(
                        "test-name-image1.jpg",
                        "test-name-image2.jpg"
                )
        );
        productService.register(productDto);
    }

}