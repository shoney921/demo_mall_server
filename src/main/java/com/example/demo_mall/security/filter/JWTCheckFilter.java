package com.example.demo_mall.security.filter;

import com.example.demo_mall.member.dto.MemberDto;
import com.example.demo_mall.security.util.JWTUtil;
import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        //true = not checking = doFilterInternal 실행안함
        //false = checking = doFilterInternal 실행함

        // Preflight요청은 체크하지 않음
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        String path = request.getRequestURI();
        log.info("check uri.............." + path);
        //api/member/ 경로의 호출은 체크하지 않음
        if (path.startsWith("/api/member/")) {
            return true;
        }
        //이미지 조회 경로는 체크하지 않음
        if (path.startsWith("/api/products/view/")) {
            return true;
        }
        //스웨거는 필터 체크하지 않음
        if (path.startsWith("/swagger-ui") || path.startsWith("/api-docs")) {
            return true;
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("-----------------JWTCheckFilter.................");
        String authHeaderStr = request.getHeader("Authorization");

        try {
            // 1. JWT Validation
            String accessToken = authHeaderStr.substring(7); //Bearer //7 JWT 문자열
            Map<String, Object> claims = JWTUtil.validateToken(accessToken);
            log.info("JWT claims: " + claims);

            // 2.토큰 validation 성공 이후
            // 2-1. 사용자 정보 를 dto화
            Integer id = (Integer) claims.get("id");
            Long kakaoId = (Long) claims.get("kakaoId");
            String email = (String) claims.get("email");
            String mobile = (String) claims.get("mobile");
            String pw = (String) claims.get("password");
            String name = (String) claims.get("name");
            String nickname = (String) claims.get("nickname");
            Boolean social = (Boolean) claims.get("social");

            List<String> roleNames = (List<String>) claims.get("roleNames");
            MemberDto memberDto = new MemberDto(id.longValue(), kakaoId, email, mobile, pw, name, nickname, social.booleanValue(),
                    roleNames);
            log.info("-----------------------------------");
            log.info(memberDto);
            log.info(memberDto.getAuthorities());

            // 2-2. SecurityContextHolder에 설정
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(memberDto, pw, memberDto.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // 3. 성공하면 다음 필터로 간다.
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            log.error("JWT Check Error..............");
            log.error(e.getMessage());

            String msg = new Gson().toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            printWriter.println(msg);
            printWriter.close();
        }
    }
}
