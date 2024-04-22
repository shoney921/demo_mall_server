package com.example.demo_mall.mallapi.repository.search;

import com.example.demo_mall.domain.QQna;
import com.example.demo_mall.domain.Qna;
import com.example.demo_mall.mallapi.dto.PageReqDto;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;


@Log4j2
public class QnaSearchImpl extends QuerydslRepositorySupport implements QnaSearch {

    public QnaSearchImpl() {
        super(Qna.class);
    }

    @Override
    public Page<Qna> search1(PageReqDto pageReqDto) {
        log.info("search1 .................");

        QQna qna = QQna.qna;

        JPQLQuery<Qna> query = from(qna);

        Pageable pageable = PageRequest.of(
                pageReqDto.getPage() - 1,
                pageReqDto.getSize(),
                Sort.by("tno").descending());

        this.getQuerydsl().applyPagination(pageable, query);

        List<Qna> list = query.fetch();
        long total = query.fetchCount();

        return new PageImpl<>(list, pageable, total);
    }
}
