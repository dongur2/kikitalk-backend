package com.kikitalk.chatting.chat.dto.response;

import com.kikitalk.chatting.chat.domain.ChatRoom;
import com.kikitalk.chatting.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Builder @AllArgsConstructor
public class ChatRoomDTO {
    private final Long chatRoomId;
    private final String name;
    private final String pic;

    private final List<MessageInChatDTO> messages;
    private final LocalDateTime latestTime;

    public static ChatRoomDTO from(ChatRoom chatRoom, User user) {
        return ChatRoomDTO.builder()
                .chatRoomId(chatRoom.getId())
                .name(user.getNickname())
                .pic(user.getProfileImage())
                .messages(chatRoom.getMessages().stream().map(MessageInChatDTO::from).toList())
                .latestTime(chatRoom.getUpdatedAt())
                .build();
    }
}
