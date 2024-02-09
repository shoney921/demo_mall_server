package com.example.demo_mall.mallapi.dto;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
class PageReqDtoTest {

    @Test
    void testDefault() {
        PageReqDto build = PageReqDto.builder().build();

        Assertions.assertEquals(1, build.getPage());
        Assertions.assertEquals(10, build.getSize());
    }

}