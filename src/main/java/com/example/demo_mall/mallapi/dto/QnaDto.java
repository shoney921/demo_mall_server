package com.example.demo_mall.mallapi.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QnaDto {
    private long qno;
    private String title;
    private String writer;
    private Boolean complete;
    private LocalDate dueDate;
}
