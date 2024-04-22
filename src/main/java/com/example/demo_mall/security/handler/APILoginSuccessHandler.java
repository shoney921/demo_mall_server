package com.example.demo_mall.security.handler;

import com.example.demo_mall.member.dto.MemberDto;
import com.example.demo_mall.security.util.JWTUtil;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Log4j2
public class APILoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("------------------------");
        log.info(authentication);
        log.info("------------------------");

        MemberDto memberDTO = (MemberDto)authentication.getPrincipal();
        Map<String, Object> claims = memberDTO.getClaims();

        String accessToken = JWTUtil.generateToken(claims, 10);
        String refreshToken = JWTUtil.generateToken(claims,60*24);

        claims.put("accessToken", accessToken);
        claims.put("refreshToken", refreshToken);

        // json 출력 (스프링 Mvc에서는 해당 내용 만들지 않아도 리턴에서 알아서 되지만, 시큐리티에서는 직접 구현해야함)
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.println(new Gson().toJson(claims));
        printWriter.close();
    }
}
