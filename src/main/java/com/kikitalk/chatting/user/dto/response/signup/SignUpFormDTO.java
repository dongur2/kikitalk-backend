package com.kikitalk.chatting.user.dto.response.signup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

//회원 가입 폼에 바인딩
@Getter @Builder @AllArgsConstructor
public class SignUpFormDTO {
    private final String nickname;
}
