package com.example.demo_mall.security.controller;

import com.example.demo_mall.security.util.CustomJWTException;
import com.example.demo_mall.security.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
public class APIRefreshController {

    @GetMapping("/api/member/refresh")
    public Map<String, String> refresh(
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
            return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
        }

        //Refresh토큰 검증
        Map<String, Object> claims = JWTUtil.validateToken(refreshToken);
        log.info("refresh ... claims: " + claims);
        String newAccessToken = JWTUtil.generateToken(claims, 10);
        String newRefreshToken = checkExpTimeOneHour((Integer) claims.get("exp")) == true ?
                JWTUtil.generateToken(claims, 60 * 24) : refreshToken;
        return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);

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

    private boolean checkExpTimeOneHour(Integer exp) { // 만료시간이 한시간 남았는지 체크
        java.util.Date expDate = new java.util.Date((long) exp * (1000));
        long gap = expDate.getTime() - System.currentTimeMillis();
        long leftMin = gap / (1000 * 60);
        return leftMin < 60;
    }
}
