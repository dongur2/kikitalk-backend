package com.doni.talk.global.security.oauth2.handler;

import com.doni.talk.global.security.oauth2.service.OAuth2UserPrincipal;
import com.doni.talk.user.domain.User;
import com.doni.talk.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j @Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserService userService;

    public OAuth2SuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(request, response, authentication);
        response.sendRedirect(targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        OAuth2UserPrincipal principal = getOAuth2UserPrincipal(authentication);
        if (principal == null) {
            return UriComponentsBuilder.fromUriString("http://localhost:8080")
                    .queryParam("error", "Login failed")
                    .build().toUriString();
        }

        //카카오 서버로부터 받아올 수 있는 닉네임, 프사URL 받아왔으므로
        //DB에 저장된 회원인지 확인하고
        User dbUser = userService.getUserBySnsId(principal.getUserInfo().getId());

        //없는 회원이면 DB 저장 - 회원가입 진행
        if(dbUser == null) {
            log.info("미가입 회원입니다.");
            Cookie cookie = new Cookie("id", principal.getUserInfo().getId());
            cookie.setAttribute("nickname", principal.getUserInfo().getNickname());
            cookie.setAttribute("pic", principal.getUserInfo().getProfileImageUrl());

            cookie.setHttpOnly(true);
            cookie.setMaxAge(60 * 5); //5분

            //회원가입 폼으로
            return UriComponentsBuilder.fromUriString("http://localhost:8080/api/v1/users/register").build().toUriString();
        }

        //있는 회원이면 로그인 진행 - 토큰 발급
        log.info("가입 회원입니다.");
        String accessToken = userService.signIn(dbUser);
        log.info("액세스 토큰 발급 완료: {}", accessToken);

        //헤더에 토큰 전달
        response.setHeader("Authorization", accessToken);

        return UriComponentsBuilder.fromUriString("http://localhost:8080/api/v1/home")
                .build().toUriString();
    }

    private OAuth2UserPrincipal getOAuth2UserPrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        return (principal instanceof OAuth2UserPrincipal) ? (OAuth2UserPrincipal) principal : null;
    }
}
