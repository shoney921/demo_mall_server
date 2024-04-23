package com.example.demo_mall.mallapi.controller.advise;

import com.example.demo_mall.mallapi.dto.ProductDto;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.NoSuchElementException;

@Aspect
@RestControllerAdvice(basePackages = {"com.example.demo_mall.mallapi.controller", "com.example.demo_mall.member"})
public class CustomControllerAdvice { // todo : [팀싱크] restControllerAdvice

    @ExceptionHandler(NoSuchElementException.class)
    public ApiResponse<?> notExist(NoSuchElementException e) {
        return ApiResponse.builder()
                .code("404")
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> notExist(MethodArgumentNotValidException e) {
        return ApiResponse.builder()
                .code("406")
                .message(e.getMessage())
                .build();
    }
}
