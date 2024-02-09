package com.example.demo_mall.mallapi.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString
@Data
@Builder
public class PageReqDto {

    @Builder.Default
    private int page = 1;
    @Builder.Default
    private int size = 10;
}
