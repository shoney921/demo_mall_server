package com.example.demo_mall.security.controller;

import com.example.demo_mall.security.dto.RefreshTokenResDto;
import com.example.demo_mall.security.util.CustomJWTException;
import com.example.demo_mall.security.util.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@Tag(name = "Refresh Token API", description = "Refresh 토큰 API")
@RestController
@Log4j2
@RequiredArgsConstructor
public class APIRefreshController {

    @Operation(summary = "Refresh Token", description = "Refresh Token 로 Access Token 재발급")
    @GetMapping("/api/member/refresh")
    public RefreshTokenResDto refresh(
            @RequestHeader("Authorization") String authHeader,
            String refreshToken) {

        if (refreshToken == null) {
            throw new CustomJWTException("NULL_REFRESH");
        }

        if (authHeader == null || authHeader.length() < 7) {
            throw new CustomJWTException("INVALID_STRING");
        }

        String accessToken = authHeader.substring(7);

        if (!checkExpiredToken(accessToken)) { // 만료되지 않음
            return RefreshTokenResDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }

        Map<String, Object> claims = JWTUtil.validateToken(refreshToken);
        log.info("refresh ... claims: " + claims);
        String newAccessToken = JWTUtil.generateToken(claims, 10);
        String newRefreshToken = checkExpTimeOneHour((Integer) claims.get("exp")) ?
                JWTUtil.generateToken(claims, 60 * 24) : refreshToken;
        return RefreshTokenResDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    private boolean checkExpiredToken(String token) {
        try {
            JWTUtil.validateToken(token);
        } catch (CustomJWTException exception) {
            if (exception.getMessage().equals("Expired")) {
                return true;
            }
        }
        return false;
    }

    private boolean checkExpTimeOneHour(Integer exp) {
        java.util.Date expDate = new java.util.Date((long) exp * (1000));
        long gap = expDate.getTime() - System.currentTimeMillis();
        long leftMin = gap / (1000 * 60);
        return leftMin < 60;
    }
}
