package com.example.demo_mall.mallapi.repository.search;

import com.example.demo_mall.domain.Product;
import com.example.demo_mall.domain.QProduct;
import com.example.demo_mall.domain.QProductImage;
import com.example.demo_mall.mallapi.dto.PageReqDto;
import com.example.demo_mall.mallapi.dto.PageResDto;
import com.example.demo_mall.mallapi.dto.ProductDto;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Objects;

@Log4j2
public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {

    public ProductSearchImpl() {
        super(Product.class);
    }

    @Override
    public PageResDto<ProductDto> searchList(PageReqDto pageReqDto) {
        log.info(" searchList ====================");

        Pageable pageable = PageRequest.of(
                pageReqDto.getPage() - 1,
                pageReqDto.getSize(),
                Sort.by("pno").descending());

        QProduct product = QProduct.product;
        QProductImage productImage = QProductImage.productImage;

        JPQLQuery<Product> query = from(product);

        query.leftJoin(product.imageList, productImage); // elementCollection 으로 조인하는 방법

        query.where(productImage.ord.eq(0), product.delFlag.eq(false));

        Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query);

//        List<Product> productList = query.fetch();
        List<Tuple> productTuple = query.select(product, productImage).fetch();

        long count = query.fetchCount();

        log.info("====================================");
        log.info(productTuple);
        log.info(count);

        return null;
    }
}
