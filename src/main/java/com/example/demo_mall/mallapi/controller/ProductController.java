package com.example.demo_mall.mallapi.controller;

import com.example.demo_mall.mallapi.controller.advise.ApiResponse;
import com.example.demo_mall.mallapi.dto.PageReqDto;
import com.example.demo_mall.mallapi.dto.PageResDto;
import com.example.demo_mall.mallapi.dto.ProductDto;
import com.example.demo_mall.mallapi.dto.ResultResDto;
import com.example.demo_mall.mallapi.service.ProductService;
import com.example.demo_mall.mallapi.util.CustomFileUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Product API", description = "제품 API")
@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final CustomFileUtil fileUtil;
    private final ProductService productService;

    @Operation(summary = "제품 리스트 조회", description = "제품 리스트 조회")
    @GetMapping("/list")
    public ApiResponse<PageResDto<ProductDto>> list(PageReqDto pageReqDto) {
        return ApiResponse.success(productService.getList(pageReqDto));
    }

    @Operation(summary = "제품 등록", description = "제품 등록")
    @PostMapping("/")
    public ApiResponse<ResultResDto> register(ProductDto productDto) {
        List<MultipartFile> files = productDto.getFiles();
        List<String> fileNames = fileUtil.saveFiles(files);
        productDto.setUploadedFileNames(fileNames);
        Long pno = productService.register(productDto);
        return ApiResponse.success(ResultResDto.builder().result(pno.toString()).build());
    }

    @Operation(summary = "제품 정보 조회", description = "제품 정보 조회")
    @GetMapping("/{pno}")
    public ApiResponse<ProductDto> read(@PathVariable("pno") Long pno) {
        ProductDto productDto = productService.get(pno);
        return ApiResponse.success(productDto);
    }

    @Operation(summary = "제품 사진 조회", description = "제품 사진 조회")
    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGet(@PathVariable("fileName") String fileName) {
        return fileUtil.getFile(fileName);
    }

    @Operation(summary = "제품 정보 변경", description = "제품 정보 변경")
    @PutMapping("/{pno}")
    public ApiResponse<?> modify(@PathVariable("pno") Long pno, ProductDto productDto) {
        productDto.setPno(pno);

        List<MultipartFile> files = productDto.getFiles();
        List<String> currentUploadFileNames = fileUtil.saveFiles(files);  // 파일 저장 후 파일 name 리스트 반환
        List<String> uploadedFileNames = productDto.getUploadedFileNames(); //
        if (currentUploadFileNames != null && !currentUploadFileNames.isEmpty()) {
            uploadedFileNames.addAll(currentUploadFileNames);
        }

        ProductDto oldProductDto = productService.get(pno); // 수정하기 전에 과거의 dto를 먼저 가져옴
        productService.modify(productDto);

        List<String> oldFileNames = oldProductDto.getUploadedFileNames();
        if (oldFileNames != null && !oldFileNames.isEmpty()) {
            List<String> collect = oldFileNames.stream()
                    .filter(fileName -> !uploadedFileNames.contains(fileName)).toList();
            fileUtil.deleteFiles(collect);
        }
        return ApiResponse.success(null);
    }

    @Operation(summary = "제품 삭제", description = "제품 삭제")
    @DeleteMapping("/{pno}")
    public ApiResponse<?> remove(@PathVariable("pno") Long pno) {
        ProductDto productDto = productService.get(pno);
        fileUtil.deleteFiles(productDto.getUploadedFileNames());
        productService.remove(pno);
        return ApiResponse.success(null);
    }
}
