package com.kikitalk.chatting.chat.config.stomp;

import com.kikitalk.chatting.chat.service.ChatService;
import com.kikitalk.chatting.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j @Component @RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TYPE = "Bearer";
    private final JwtProvider jwtProvider;
    private final ChatService chatService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        final StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

        //연결
        if(StompCommand.CONNECT == headerAccessor.getCommand()) {
            log.info("CONNECT 요청 :: 토큰 검증");
            extractAndValidateToken(headerAccessor);
        }

        //구독
        else if(StompCommand.SUBSCRIBE == headerAccessor.getCommand()) {
            log.info("SUBSCRIBE 요청");
            String token = extractAndValidateToken(headerAccessor);

            Long userId = jwtProvider.getUserId(token);
            String chatRoomId = headerAccessor.getDestination().split("/")[2];

            if(!chatService.isRoomParticipant(userId, Long.parseLong(chatRoomId)))
                throw new AuthenticationServiceException("해당 채팅방에 권한이 없습니다.");
        }

        return message;
    }

    private String extractAndValidateToken(StompHeaderAccessor headerAccessor) {
        String bearerToken = headerAccessor.getFirstNativeHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
            String token = bearerToken.substring(7);
            jwtProvider.validateToken(token);
            log.info("토큰 검증 완료");
            return token;
        }
        return null;
    }
}
