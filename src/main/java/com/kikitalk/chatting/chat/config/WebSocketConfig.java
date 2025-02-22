package com.kikitalk.chatting.chat.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

//기본 WebSocket 연결 설정
@RequiredArgsConstructor
@Configuration @EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final WebSocketHandler webSocketHandler;
    private final WebSocketAuthInterceptor webSocketAuthInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/connect")
                .addInterceptors(webSocketAuthInterceptor)
                .setAllowedOrigins("*");
    }
}
