package com.kikitalk.chatting.chat.dto.response;

import com.kikitalk.chatting.chat.domain.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter @Builder @AllArgsConstructor
public class MessageInChatDTO {
    private final Long id;
    private final String content;
    private final LocalDateTime createdAt;

    private final Long writerId;
    private final String nickname;

    public static MessageInChatDTO from(ChatMessage message) {
        return MessageInChatDTO.builder()
                .id(message.getId())
                .content(message.getContent())
                .writerId(message.getUser().getId())
                .nickname(message.getUser().getNickname())
                .createdAt(message.getCreatedAt())
                .build();
    }
}

