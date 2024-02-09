package com.example.demo_mall.mallapi.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResDto<E> {

    private List<E> dtoList;

    private List<Integer> pageNumList;

    private PageReqDto pageReqDto;

    private boolean prev, next;

    private int totalCount, prevPage, nextPage, totalPage, current;

    @Builder(builderMethodName = "withAll")
    public PageResDto(List<E> dtoList, PageReqDto pageReqDto, long total) {
        this.dtoList = dtoList;
        this.pageReqDto = pageReqDto;
        this.totalCount = (int) total;

        // 아래 페이지 숫자의 끝 페이지 (아래 페이지에서는 10개의 리스틀 보여주기 때문에 하드코딩)
        int end = (int) (Math.ceil(pageReqDto.getPage() / 10.0)) * 10;
        int start = end - 9;

        // 최종 마지막 페이지
        int last = (int) (Math.ceil(totalCount / (double) pageReqDto.getSize()));
        end = Math.min(end, last);

        this.prev = start > 1;
        this.next = totalCount > end * pageReqDto.getSize();

        this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

        this.prevPage = prev ? start - 1 : 0;
        this.nextPage = next ? end + 1 : 0;

        this.totalPage = this.pageNumList.size();
        this.current = pageReqDto.getPage();
    }
}
