package com.kikitalk.chatting.user.dto.response;

import com.kikitalk.chatting.user.domain.User;
import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class SimpleProfileDTO {
    Long userId;
    String showName;
    String profileImg;
    String message;

    public static SimpleProfileDTO from(User user) {
        String showName = user.getNickname() == null ? user.getName() : user.getNickname();

        return SimpleProfileDTO.builder()
                .userId(user.getId())
                .showName(showName)
                .profileImg(user.getProfileImage())
                .message(user.getMessage())
                .build();
    }
}
