package com.example.demo_mall.mallapi.service;

import com.example.demo_mall.mallapi.domain.Product;
import com.example.demo_mall.mallapi.domain.ProductImage;
import com.example.demo_mall.mallapi.dto.PageReqDto;
import com.example.demo_mall.mallapi.dto.PageResDto;
import com.example.demo_mall.mallapi.dto.ProductDto;
import com.example.demo_mall.mallapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public PageResDto<ProductDto> getList(PageReqDto pageReqDto) {

        Pageable pageable = PageRequest.of(
                pageReqDto.getPage() - 1,
                pageReqDto.getSize(),
                Sort.by("pno").descending());

        Page<Object[]> result = productRepository.selectList(pageable);

        List<ProductDto> collect = result.get().map(arr -> {
            Product product = (Product) arr[0];
            ProductImage productImage = (ProductImage) arr[1];

            ProductDto productDto = ProductDto.builder()
                    .pno(product.getPno())
                    .pname(product.getPname())
                    .pdesc(product.getPdesc())
                    .delFlag(product.getDelFlag())
                    .price(product.getPrice())
                    .build();

            String imageStr = productImage.getFileName();
            productDto.setUploadedFileNames(List.of(imageStr));

            return productDto;
        }).toList();

        long totalCount = result.getTotalElements();


        return PageResDto.<ProductDto>withAll()
                .dtoList(collect)
                .total(totalCount)
                .pageReqDto(pageReqDto)
                .build();
    }

    @Override
    public Long register(ProductDto productDto) {
        Product product = dtoToEntity(productDto);
        return productRepository.save(product).getPno();
    }

    @Override
    public ProductDto get(Long pno) {
        Optional<Product> productOptional = productRepository.findById(pno);
        Product product = productOptional.orElseThrow();
        return entityToDto(product);
    }

    @Override
    public void modify(ProductDto productDto) {
        Optional<Product> productOptional = productRepository.findById((productDto.getPno()));
        Product product = productOptional.orElseThrow();

        product.setPrice(productDto.getPrice());
        product.setPdesc(productDto.getPdesc());
        product.setPname(productDto.getPname());
        product.setDelFlag(productDto.isDelFlag());

        List<String> uploadFileNames = productDto.getUploadedFileNames();

        product.clearList();

        if (uploadFileNames != null && !uploadFileNames.isEmpty()) {
            for (String uploadName : uploadFileNames) {
                product.addImageString(uploadName);
            }
        }
        productRepository.save(product);
    }

    @Override
    public void remove(Long pno) {
        productRepository.deleteById(pno);
    }

    private ProductDto entityToDto(Product product) {
        ProductDto productDto = ProductDto.builder()
                .pno(product.getPno())
                .pname(product.getPname())
                .pdesc(product.getPdesc())
                .delFlag(product.getDelFlag())
                .price(product.getPrice())
                .build();

        List<ProductImage> imageList = product.getImageList();

        if (imageList == null || imageList.isEmpty()) {
            return productDto;
        }
        List<String> fileNameList = imageList.stream().map(ProductImage::getFileName).toList();

        productDto.setUploadedFileNames(fileNameList);

        return productDto;
    }

    private Product dtoToEntity(ProductDto productDto) {
        Product product = Product.builder()
                .pno(productDto.getPno())
                .pname(productDto.getPname())
                .pdesc(productDto.getPdesc())
                .price(productDto.getPrice())
                .build();

        List<String> uploadedFileNames = productDto.getUploadedFileNames();

        if (uploadedFileNames == null || uploadedFileNames.isEmpty()) {
            return product;
        }

        uploadedFileNames.forEach(fileName -> {
            product.addImageString(fileName);
        });

        return product;
    }

}
