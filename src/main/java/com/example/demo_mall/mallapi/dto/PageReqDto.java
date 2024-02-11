package com.example.demo_mall.mallapi.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@ToString
@Data
@Builder
@NoArgsConstructor // Todo : [팀싱크] 쿼리 파라미터가 없을 때, 디폴트 값으로 사용하고 싶으면 NoArgs 사용해야함
@AllArgsConstructor
public class PageReqDto {

    @Builder.Default
    private int page = 1;
    @Builder.Default
    private int size = 10;
}
