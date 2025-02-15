package com.kikitalk.chatting.global.security.oauth2.handler;

import com.kikitalk.chatting.global.security.oauth2.service.OAuth2UserPrincipal;
import com.kikitalk.chatting.global.security.oauth2.userInfo.OAuth2UserInfo;
import com.kikitalk.chatting.user.domain.User;
import com.kikitalk.chatting.user.dto.request.signup.UserSaveDTO;
import com.kikitalk.chatting.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j @Component @RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Value("${auth.url.home}") private String HOME_URL;
    @Value("${auth.url.login}") private String LOGIN_URL;

    @Autowired private final UserService userService;

    @Autowired private RedisTemplate<String, String> redisTemplate;
    @Value("${auth.redis.key_expire}") private Long KEY_EXPIRE_PERIOD;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(request, response, authentication);
        response.sendRedirect(targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        OAuth2UserPrincipal principal = getOAuth2UserPrincipal(authentication);
        if (principal == null) {
            return UriComponentsBuilder.fromUriString(HOME_URL)
                    .queryParam("error", "Login failed")
                    .build().toUriString();
        }

        User user;
        String randomKeyForToken;

        //카카오 서버로부터 받아올 수 있는 닉네임, 프사URL 받아왔으므로
        try {
            //DB에 저장된 회원인지 확인
            user = userService.getUserBySnsId(principal.getUserInfo().getId());

        } catch (NullPointerException e) {
            //없는 회원이면 회원가입 진행: OAuth2로 받아온 데이터
            user = registerNewUserWithOAuth2Info(principal);
        }

        //로그인: 토큰 발급 및 키 리턴
        randomKeyForToken = loginAndCreateToken(user);

        //로그인 단계 화면으로 (토큰 조회)
        return UriComponentsBuilder.fromUriString(LOGIN_URL)
                .queryParam("key", randomKeyForToken)
                .build().toUriString();
    }

    private OAuth2UserPrincipal getOAuth2UserPrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        return (principal instanceof OAuth2UserPrincipal) ? (OAuth2UserPrincipal) principal : null;
    }

    private User registerNewUserWithOAuth2Info(OAuth2UserPrincipal principal) {
        OAuth2UserInfo info = principal.getUserInfo();
        return userService.save(new UserSaveDTO(info.getId(), info.getNickname(), info.getProfileImageUrl()));
    }

    private String loginAndCreateToken(User user) {
        String randomKey = UUID.randomUUID().toString();
        String accessToken = userService.signIn(user);

        saveKeyAndTokenOnRedis(randomKey, accessToken); //redis에 저장
        return randomKey;
    }

    private void saveKeyAndTokenOnRedis(String key, String token) {
        redisTemplate.opsForValue().set(
                key,
                token,
                KEY_EXPIRE_PERIOD,
                TimeUnit.SECONDS
        );
    }
}
