package com.example.demo_mall.mallapi.controller.advise;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {
    private String code;
    private String message;
    private T data;

    // 성공 시 ApiResponse를 생성하는 정적 메서드
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .code("200")
                .message("success")
                .data(data)
                .build();
    }

    // 실패 시 ApiResponse를 생성하는 정적 메서드 (예시)
    public static <T> ApiResponse<T> error(String code, String message) {
        return ApiResponse.<T>builder()
                .code(code)
                .message(message)
                .build();
    }
}
