package com.doni.talk.user.dto.response;

import com.doni.talk.user.domain.User;
import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class DetailProfileDTO {
    String name;
    String nickname;
    String message;
    String profileImg;

    public static DetailProfileDTO from(User user) {
        return DetailProfileDTO.builder()
                .name(user.getName())
                .nickname(user.getNickname())
                .message(user.getMessage())
                .profileImg(user.getProfileImage())
                .build();
    }
}
