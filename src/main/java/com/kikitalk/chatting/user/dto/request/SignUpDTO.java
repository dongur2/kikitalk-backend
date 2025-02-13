package com.kikitalk.chatting.user.dto.request;

import com.kikitalk.chatting.user.domain.User;
import lombok.*;

@Setter @Getter @AllArgsConstructor
public class SignUpDTO {
    String snsId;
    String nickname;
    String profileImage;

    public User to() {
        return User.builder()
                .snsId(snsId)
                .nickname(nickname)
                .profileImage(profileImage)
                .build();
    }
}
