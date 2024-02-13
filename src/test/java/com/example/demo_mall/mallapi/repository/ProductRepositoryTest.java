package com.example.demo_mall.mallapi.repository;

import com.example.demo_mall.mallapi.domain.Product;
import com.example.demo_mall.mallapi.dto.PageReqDto;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testInsertInit() {
        for (int i = 0; i < 40; i++) {
            Product product = Product.builder()
                    .pname("test" + Integer.toString(i))
                    .pdesc("test desc "+ Integer.toString(i))
                    .price(i * 1000)
                    .build();

            product.addImageString(UUID.randomUUID() + "_Image1.jpg" + i);
            product.addImageString(UUID.randomUUID() + "_Image2.jpg" + i);

            productRepository.save(product);
        }
    }

    @Test
    void testInsert() {
        Product product = Product.builder()
                .pname("test")
                .pdesc("test desc")
                .price(10000)
                .build();

        product.addImageString(UUID.randomUUID() + "_Image1.jpg");
        product.addImageString(UUID.randomUUID() + "_Image2.jpg");

        productRepository.save(product);
    }

    @Test
    void testRead1() {

    }

    @Test
    void testRead2() {
        Long pno = 1L;

        Optional<Product> result = productRepository.selectOne(pno);
        Product product = result.orElseThrow();

        log.info(product);
        log.info(product.getImageList());
    }

    @Commit
    @Transactional
    @Test
    void testDelete() {
        Long pno = 2L;

        productRepository.updateToDelete(true, pno);
    }

    @Transactional
    @Commit
    @Test
    void testUpdate() {
        Product product = productRepository.selectOne(1L).get();
        product.setPrice(13000);
        product.clearList();
        product.addImageString(UUID.randomUUID() + "_ImageP11.jpg");
        product.addImageString(UUID.randomUUID() + "_ImageP21.jpg");
        product.addImageString(UUID.randomUUID() + "_ImageP31.jpg");
    }

    @Test
    public void testList() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("pno").descending());
        Page<Object[]> result = productRepository.selectList(pageable);
        result.getContent().forEach(arr -> log.info(Arrays.toString(arr)));
    }

    @Test
    public void testSearch() {

        PageReqDto pageReqDto = PageReqDto.builder().build();

        productRepository.searchList(pageReqDto);
    }
}