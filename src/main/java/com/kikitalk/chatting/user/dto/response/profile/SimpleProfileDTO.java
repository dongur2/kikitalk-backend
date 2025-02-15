package com.kikitalk.chatting.user.dto.response.profile;

import com.kikitalk.chatting.user.domain.User;
import lombok.*;

//친구 목록 화면에서의 프로필
@Getter @Builder @AllArgsConstructor
public class SimpleProfileDTO {
    private final Long userId;
    private final String nickname;
    private final String profileImage;
    private final String message;

    public static SimpleProfileDTO from(User user) {
        return SimpleProfileDTO.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileImage(user.getProfileImage())
                .message(user.getMessage())
                .build();
    }
}
