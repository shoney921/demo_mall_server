package com.example.demo_mall.security.util;

import com.example.demo_mall.member.dto.LoginResDto;
import com.example.demo_mall.member.dto.MemberDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.WeakKeyException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;

import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

@Log4j2
public class JWTUtil {

    private static String key = "1234567890123456789012345678901234567890";

    public static LoginResDto convertMemberDtoToLoginResDto(MemberDto memberDto) {
        String jwtAccessToken = JWTUtil.generateToken(memberDto.getClaims(), 10);
        String jwtRefreshToken = JWTUtil.generateToken(memberDto.getClaims(), 60 * 24);
        LoginResDto loginResDto = LoginResDto.builder()
                .accessToken(jwtAccessToken)
                .refreshToken(jwtRefreshToken)
                .build();
        BeanUtils.copyProperties(memberDto, loginResDto);
        return loginResDto;
    }


    public static String generateToken(Map<String, Object> valueMap, int min) {
        SecretKey key = null;
        try{
            key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return Jwts.builder()
                .setHeader(Map.of("typ","JWT"))
                .setClaims(valueMap)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .signWith(key)
                .compact();
    }

    public static Map<String, Object> validateToken(String token) {
        Map<String, Object> claim = null;
        try {
            SecretKey key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
            claim = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token) // 파싱 및 검증, 실패 시 에러
                    .getBody();
        } catch (MalformedJwtException malformedJwtException) {
            throw new CustomJWTException("MalFormed");
        } catch (ExpiredJwtException expiredJwtException) {
            throw new CustomJWTException("Expired");
        } catch (UnsupportedJwtException | WeakKeyException | UnsupportedEncodingException | IllegalArgumentException |
                 SignatureException e) {
            throw new CustomJWTException("Error");
        }
        return claim;
    }

}
