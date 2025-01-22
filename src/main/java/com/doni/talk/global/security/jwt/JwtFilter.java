package com.doni.talk.global.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = jwtProvider.resolveToken(request);                     //요청으로부터 토큰 추출
        if (jwt != null && jwtProvider.validateToken(jwt)) {                //토큰 검증
            Authentication auth = jwtProvider.getAuthentication(jwt);       //인증 객체 생성
            SecurityContextHolder.getContext().setAuthentication(auth);     //SecurityContextHolder에 인증 객체 저장
        }
        filterChain.doFilter(request, response);
    }
}
