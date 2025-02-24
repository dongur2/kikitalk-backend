package com.kikitalk.chatting.chat.config.stomp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/*
* 세션관리
* 연결 및 해제 이벤트 기록 -> 연결된 세션 수를 실시간으로 확인
* */
@Component @Slf4j
public class StompEventListener {
    private final Set<String> sessions = ConcurrentHashMap.newKeySet();

    @EventListener
    public void connectHandle(SessionConnectEvent event){
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        sessions.add(accessor.getSessionId());
        log.debug("연결된 세션 ID: {}, 전체 세션 수: {}", accessor.getSessionId(), sessions.size());
    }

    @EventListener
    public void disconnectHandle(SessionDisconnectEvent event){
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        sessions.remove(accessor.getSessionId());
        log.debug("해제된 세션 ID: {}, 전체 세션 수: {}", accessor.getSessionId(), sessions.size());
    }
}
