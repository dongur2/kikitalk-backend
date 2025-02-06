package com.kikitalk.chatting.global.security.oauth2.handler;

import com.kikitalk.chatting.global.security.oauth2.service.OAuth2UserPrincipal;
import com.kikitalk.chatting.user.domain.User;
import com.kikitalk.chatting.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j @Component @RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Value("${auth.url.login}") private String LOGIN_URL;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(request, response, authentication);
        response.sendRedirect(targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        OAuth2UserPrincipal principal = getOAuth2UserPrincipal(authentication);
        if (principal == null) {
            return UriComponentsBuilder.fromUriString(LOGIN_URL)
                    .queryParam("error", "Login failed")
                    .build().toUriString();
        }

        //카카오 서버로부터 받아올 수 있는 닉네임, 프사URL 받아왔으므로
        try {
            //DB에 저장된 회원인지 확인
            User dbUser = userService.getUserBySnsId(principal.getUserInfo().getId());

            //있는 회원이면 로그인 진행: 토큰 발급
            String accessToken = userService.signIn(dbUser);
            log.info("액세스 토큰 발급: {}", accessToken);

            //헤더에 토큰 전달
            response.setHeader("Authorization", accessToken);

        } catch (NullPointerException e) {
            //없는 회원이면 회원가입 진행: OAuth2로 받아온 데이터 전달
            bindUserInfoFromOAuth2OnCookie(response, principal);

            //회원가입 폼으로
            return UriComponentsBuilder.fromUriString("http://localhost:8080/api/v1/users/register").build().toUriString();
        }

        return UriComponentsBuilder.fromUriString(LOGIN_URL).build().toUriString();
    }

    private OAuth2UserPrincipal getOAuth2UserPrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        return (principal instanceof OAuth2UserPrincipal) ? (OAuth2UserPrincipal) principal : null;
    }

    private void bindUserInfoFromOAuth2OnCookie(HttpServletResponse response, OAuth2UserPrincipal principal) {
        Map<String, String> info = new HashMap<>();
        info.put("id", principal.getUserInfo().getId());
        info.put("nickname", URLEncoder.encode(principal.getUserInfo().getNickname(), StandardCharsets.UTF_8));
        info.put("pic", principal.getUserInfo().getProfileImageUrl());

        info.forEach((key, value) -> response.addCookie(makeCookie(key, value)));
    }

    private Cookie makeCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);

        cookie.setPath("http://localhost:8080/api/v1/users/register");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 5); // 5분

        return cookie;
    }
}
