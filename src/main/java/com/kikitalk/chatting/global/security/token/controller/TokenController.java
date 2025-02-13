package com.kikitalk.chatting.global.security.token.controller;

import com.kikitalk.chatting.global.response.CommonResponse;
import com.kikitalk.chatting.global.security.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/token")
public class TokenController {
    private final TokenService tokenService;

    @GetMapping
    public CommonResponse<String> getToken(@RequestParam("key") String key) {
        String accessToken = tokenService.getAccessToken(key);
        return CommonResponse.of("토큰을 조회 완료했습니다.", accessToken);
    }
}
