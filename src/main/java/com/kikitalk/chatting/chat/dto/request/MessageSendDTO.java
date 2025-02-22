package com.kikitalk.chatting.chat.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor @AllArgsConstructor
public class MessageSendDTO {
    private Long chatRoomId;
    private Long writerId;
    private String content;
}

