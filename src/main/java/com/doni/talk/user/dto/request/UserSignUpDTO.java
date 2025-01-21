package com.doni.talk.user.dto.request;

import com.doni.talk.user.domain.User;
import lombok.*;

@Setter @Getter @ToString
@AllArgsConstructor @NoArgsConstructor
public class UserSignUpDTO {
    String snsId;
    String name;
    String nickname;
    String message;
    String phone;
    String profileImage;

    public User to() {
        return User.builder()
                .snsId(snsId)
                .name(name)
                .nickname(nickname)
                .message(message)
                .phone(phone)
                .profileImage(profileImage)
                .build();
    }
}
