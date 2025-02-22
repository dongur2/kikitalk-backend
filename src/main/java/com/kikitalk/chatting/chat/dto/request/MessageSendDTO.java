package com.kikitalk.chatting.chat.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter @NoArgsConstructor @AllArgsConstructor
public class MessageSendDTO {
    public enum MessageType{
        JOIN, TALK, LEAVE
    }

    private MessageType messageType;
    private Long chatRoomId;
    private Long writerId;
    private String content;
}

