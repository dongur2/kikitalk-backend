package com.kikitalk.chatting.global.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j @AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = jwtProvider.resolveToken(request);                     //요청으로부터 토큰 추출

        try {
            if (jwt != null && jwtProvider.validateToken(jwt)) {                //토큰 검증
                Authentication auth = jwtProvider.getAuthentication(jwt);       //인증 객체 생성
                SecurityContextHolder.getContext().setAuthentication(auth);     //SecurityContextHolder에 인증 객체 저장
            }

        //토큰 인증 예외 전달
        } catch (MalformedJwtException | ExpiredJwtException e) {
            log.warn("토큰 인증 에러: {}", e.getMessage());
            request.setAttribute("exception", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
