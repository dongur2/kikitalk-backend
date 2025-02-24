package com.kikitalk.chatting.chat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kikitalk.chatting.chat.dto.request.StompMessageDTO;
import com.kikitalk.chatting.chat.dto.response.MessageInChatDTO;
import com.kikitalk.chatting.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequiredArgsConstructor @Slf4j
public class StompController {
    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatService chatService;

    //메세지 수신 /pub/{chatRoomId}
    @MessageMapping("/{chatRoomId}")
    public void sendMessage(@DestinationVariable("chatRoomId") Long chatRoomId, StompMessageDTO messageDto) throws JsonProcessingException {
        MessageInChatDTO chatMessage = chatService.createChatMessage(chatRoomId, messageDto);
        messagingTemplate.convertAndSend("/topic/"+chatRoomId, chatMessage);
    }
}
