package com.kikitalk.chatting.global.security.token.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j @Service
@RequiredArgsConstructor
public class TokenServiceI implements TokenService {
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public String getAccessToken(String key) {
        String token = redisTemplate.opsForValue().get(key);
        redisTemplate.delete(key); //조회 후 토큰 삭제
        return token;
    }
}