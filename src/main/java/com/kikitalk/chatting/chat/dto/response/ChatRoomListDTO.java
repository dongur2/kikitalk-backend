package com.kikitalk.chatting.chat.dto.response;

import com.kikitalk.chatting.chat.domain.ChatMessage;
import com.kikitalk.chatting.chat.domain.ChatRoom;
import com.kikitalk.chatting.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter @Builder @AllArgsConstructor
public class ChatRoomListDTO {
    private final Long id;
    private final String pic;
    private final String name;
    private final String latestMessage;
    private final LocalDateTime latestTime;

    public static ChatRoomListDTO from(ChatRoom chatRoom, User participant, ChatMessage message) {
        return ChatRoomListDTO.builder()
                .id(chatRoom.getId())
                .pic(participant.getProfileImage())
                .name(participant.getNickname())
                .latestMessage(message.getContent())
                .latestTime(message.getCreatedAt())
                .build();
    }
}
