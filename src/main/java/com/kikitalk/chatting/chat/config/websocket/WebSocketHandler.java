package com.kikitalk.chatting.chat.config.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kikitalk.chatting.chat.dto.request.MessageSendDTO;
import com.kikitalk.chatting.chat.dto.response.MessageInChatDTO;
import com.kikitalk.chatting.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

// '/connect'로 웹소켓 연결 요청이 들어왔을 때 처리
@Component @Slf4j @RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
    private final Map<Long, Set<WebSocketSession>> sessionMap = new ConcurrentHashMap<>();

    private final ChatService chatService;

    // 웹소켓 연결 확인
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        log.debug("웹소켓 세션이 연결되었습니다. ID: {}", session.getId());
    }

    // 웹소켓 메세지 처리
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        //클라이언트가 전송한 메세지를 DTO로 변환
        MessageSendDTO chatMessageDto = objectMapper.readValue(payload, MessageSendDTO.class);

        //새로 채팅방 개설: 세션을 맵에 추가
        if(chatMessageDto.getMessageType().equals(MessageSendDTO.MessageType.JOIN)) {
            sessionMap.computeIfAbsent(chatMessageDto.getChatRoomId(), s -> ConcurrentHashMap.newKeySet()).add(session);

        //채팅방 나가기: 관련 세션을 맵에서 삭제
        } else if (chatMessageDto.getMessageType().equals(MessageSendDTO.MessageType.LEAVE)) {
            sessionMap.get(chatMessageDto.getChatRoomId()).remove(session);

        //일반 메세지
        } else {
            //DTO -> DB 저장 후 응답 메세지 DTO 반환
            MessageInChatDTO chatMessage = chatService.createChatMessage(chatMessageDto);

            //응답 메세지 DTO를 String으로 변환
            String responsePayload = objectMapper.writeValueAsString(chatMessage);

            // 채팅방의 모든 클라이언트에게 메세지 전송
            for (WebSocketSession s : sessionMap.get(chatMessageDto.getChatRoomId())){
                s.sendMessage(new TextMessage(responsePayload));
            }
        }
    }

    // 웹소켓 연결 종료 확인
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        log.debug("세션이 닫혔습니다. ID: {}", session.getId());
    }
}
