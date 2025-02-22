package com.kikitalk.chatting.chat.config;

import com.kikitalk.chatting.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component @RequiredArgsConstructor @Slf4j
public class WebSocketAuthInterceptor implements HandshakeInterceptor {
    private final JwtProvider jwtProvider;

    //유저 인증 (쿼리파라미터)
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String token = request.getURI().getQuery();
        if (token != null && !token.isEmpty()) token = token.split("=")[1];
        return token != null && jwtProvider.validateToken(token);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) { }
}
