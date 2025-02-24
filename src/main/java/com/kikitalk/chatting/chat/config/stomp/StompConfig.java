package com.kikitalk.chatting.chat.config.stomp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Slf4j @Configuration @EnableWebSocketMessageBroker @RequiredArgsConstructor
public class StompConfig implements WebSocketMessageBrokerConfigurer {
    private final StompHandler stompHandler;

    //엔드포인트
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp-connect")
                .setAllowedOrigins("http://localhost:5173")
                .withSockJS(); //http 엔드포인트 허용
    }

    //라우팅
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/pub"); //메세지 발행 url -> @Controller @MessageMapping으로 라우팅
        registry.enableSimpleBroker("/topic"); //메세지 수신 url
    }

    //웹소켓 요청시 HTTP Header ... 메세지 전달 -> 인터셉터로 가로채 토큰 검증
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }
}
