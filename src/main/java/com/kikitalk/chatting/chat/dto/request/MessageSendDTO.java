package com.kikitalk.chatting.chat.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter @NoArgsConstructor @AllArgsConstructor
public class MessageSendDTO {
    @Length(min = 1, max = 500, message = "최소 1글자 ~ 최대 500자 사이로 입력해주세요.")
    private String content;
}

