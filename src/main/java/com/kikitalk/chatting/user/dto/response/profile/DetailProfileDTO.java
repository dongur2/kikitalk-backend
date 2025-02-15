package com.kikitalk.chatting.user.dto.response.profile;

import com.kikitalk.chatting.user.domain.User;
import lombok.*;

//상세 프로필
@Getter @Builder @AllArgsConstructor
public class DetailProfileDTO {
    private final Long userId;
    private final String nickname;
    private final String message;
    private final String profileImage;
    private final String birth;

    public static DetailProfileDTO from(User user) {
        return DetailProfileDTO.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .message(user.getMessage())
                .profileImage(user.getProfileImage())
                .birth(user.getBirth())
                .build();
    }
}
