package com.kikitalk.chatting.chat.dto.request;

import lombok.*;

@ToString
@Getter @NoArgsConstructor @AllArgsConstructor
public class StompMessageDTO {
    private Long chatRoomId;
    private Long writerId;
    private String content;
}

